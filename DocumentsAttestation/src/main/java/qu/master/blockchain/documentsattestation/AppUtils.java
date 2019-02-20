package qu.master.blockchain.documentsattestation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import qu.master.blockchain.documentsattestation.models.BeansRepository;
import qu.master.blockchain.documentsattestation.models.beans.Client;

public class AppUtils {
	
	public static void log(String message) {
		System.out.println(message);
	}
	
	public static String getCurrentUserId() {
		return "6aefdda3-c1c8-4a8e-a5d6-7d314b6a2994";
	}
	
	public static Client getCurrentClient() {
		try {
			BeansRepository repo = new BeansRepository();
			return repo.getClients().get(0);
		}
		
		catch (Exception e) {
			throw getError(e);
		}
	}
	
	public static void emptyDirectory(String dirPath) {
		File dir = new File(dirPath);
		if (!dir.isDirectory()) {
			throw new IllegalArgumentException("The provided path is not directory !!");
		}
		
		for(File file : dir.listFiles()) {
			if (file.isFile()) {
				file.delete();
			}
		}
	}
	
	public static String getRandomId() {
		return UUID.randomUUID().toString();
	}
	
	public static String getHex(byte[] bytes) {
		//Logger.log("Get Hex = " + new BigInteger(bytes).toString());
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < bytes.length; i++) {
			String hex = String.format("%02X", bytes[i]);
			sb.append(hex.toUpperCase());
		}
		
		return sb.toString();
	}
	
	public static byte[] parseHex(String hex) {
		BigInteger value = new BigInteger(hex, 16);
		//Logger.log("Parse Hex = " + value.toString());
		return value.toByteArray();
	}
	
	public static String formatDate(LocalDateTime date, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return date.format(formatter);
	}
	
	public static void writeBytes(byte[] bytes, String filePath) {
		try {
			Path path = Paths.get(filePath);
			Files.write(path, bytes);
		}
		
		catch (Exception e) {
			throw getError(e);
		}
	}
	
	public static byte[] readBytes(String filePath) {
		try {
			Path path = Paths.get(filePath);
			return Files.readAllBytes(path);
		}
		
		catch (Exception e) {
			throw getError(e);
		}
	}
	
	public static String getHash(byte[] data)  {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(data);
			return getHex(hash);
		}
		
		catch (Exception e) {
			throw getError(e);
		}
	}
	public static String getHash(String input) {
		return getHash(input.getBytes(StandardCharsets.UTF_8));
	}
	
	public static KeyPair generatePair(int keySize, String algo) {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(algo);
			kpg.initialize(keySize);
			return kpg.generateKeyPair();
		}
		
		catch (Exception e) {
			throw getError(e);
		}
	}
	
	public static byte[] signData(byte[] data, PrivateKey privateKey, String algo) {
		try {
			Signature sig = Signature.getInstance(algo);
			sig.initSign(privateKey);
			for(byte dataByte : data) {
				sig.update(dataByte);
			}
			
			return sig.sign();
		}
		
		catch (Exception e) {
			throw getError(e);
		}
	}
	
	public static boolean verifyData(byte[] data, PublicKey publicKey, String algo) {
		try {
			Signature sig = Signature.getInstance(algo);
			sig.initVerify(publicKey);
			for(byte dataByte : data) {
				sig.update(dataByte);
			}
			
			return sig.verify(data);
		}
		
		catch (Exception e) {
			throw getError(e);
		}
	}
	
	public static PublicKey bytesToPublicKey(String algo, byte[] bytes) {
		try {
			X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);
			KeyFactory kf = KeyFactory.getInstance(algo);
			return kf.generatePublic(ks);
		}
		
		catch (Exception e) {
			throw getError(e);
		}
	}
	
	public static PrivateKey bytesToPrivateKey(String algo, byte[] bytes) {
		try {
			PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
			KeyFactory kf = KeyFactory.getInstance(algo);
			return kf.generatePrivate(ks);
		}
		
		catch (Exception e) {
			throw getError(e);
		}
	}
	
	public static void saveKeyPair(KeyPair keyPair, String password, String publicKeyPath, String privateKeyPath, String cryptoAlgo) {
		try {
			writeBytes(keyPair.getPublic().getEncoded(), publicKeyPath);
			encryptPrivateKey(keyPair.getPrivate(), privateKeyPath, password, cryptoAlgo);
		}
		
		catch (Exception e) {
			throw getError(e);
		}
	}
	
	public static KeyPair loadKeyPair(String password, String publicKeyPath, String privateKeyPath, String publicKeyAlgo, String cryptoAlgo) {
		try {
			byte[] publicKeyBytes = readBytes(publicKeyPath);
			PublicKey publicKey = bytesToPublicKey(publicKeyAlgo, publicKeyBytes);
			PrivateKey privateKey = decryptPrivateKey(privateKeyPath, password, cryptoAlgo);
			return new KeyPair(publicKey, privateKey);
		}
		
		catch (Exception e) {
			throw getError(e);
		}
	}
	
	public static void encryptPrivateKey(PrivateKey privateKey, String keyPath, String password, String algo) {
		try {
			int iterationsCount = 20;
			int saltLength = 8;
			byte[] salt = new byte[saltLength];
			SecureRandom sr = new SecureRandom();
			sr.nextBytes(salt);
			
			PBEParameterSpec ps = new PBEParameterSpec(salt,iterationsCount);
			PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
			SecretKeyFactory skf = SecretKeyFactory.getInstance(algo);
			SecretKey sk = skf.generateSecret(keySpec);
			
			Cipher keyCipher = Cipher.getInstance(algo);
			keyCipher.init(Cipher.ENCRYPT_MODE, sk, ps);
			byte[] cipherKey = keyCipher.doFinal(privateKey.getEncoded());
			
			AlgorithmParameters algoParams = AlgorithmParameters.getInstance(algo);
			algoParams.init(ps);
			EncryptedPrivateKeyInfo result = new EncryptedPrivateKeyInfo(algoParams, cipherKey);
			writeBytes(result.getEncoded(), keyPath);
		}
		
		catch (Exception e) {
			throw getError(e);
		}
	}
	
	public static PrivateKey decryptPrivateKey(String keyPath, String password, String algo) {
		try {
			byte[] keyBytes = readBytes(keyPath);
			EncryptedPrivateKeyInfo pki = new EncryptedPrivateKeyInfo(keyBytes);
			
			Cipher keyCipher = Cipher.getInstance(pki.getAlgName());
			PBEKeySpec pbeSpec = new PBEKeySpec(password.toCharArray());
			SecretKeyFactory skf = SecretKeyFactory.getInstance(pki.getAlgName());
			SecretKey secretKey = skf.generateSecret(pbeSpec);
			
			keyCipher.init(Cipher.DECRYPT_MODE, secretKey, pki.getAlgParameters());
			
			PKCS8EncodedKeySpec pcsEncoded = pki.getKeySpec(keyCipher);
			KeyFactory privateKeyFactory = KeyFactory.getInstance(algo);
			return privateKeyFactory.generatePrivate(pcsEncoded);
		}
		
		catch (Exception e) {
			throw getError(e);
		}
	}
	
	public static SecretKey generateSecretKey() throws Exception{
		int keySize = 256;
		String algo = "AES";
		KeyGenerator kgen = KeyGenerator.getInstance(algo);
		kgen.init(keySize);
		return kgen.generateKey();
		
	}
	
	
	public static String encryptFile(PublicKey publicKey, String inputFile, String outputFile)  {
		try {
			SecretKey secretKey = generateSecretKey();
			String algo = "AES";
			Cipher cipher = Cipher.getInstance(algo);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] input = readBytes(inputFile);
			byte[] result = cipher.doFinal(input);
			writeBytes(result, outputFile);
			String encryptedSecretKey = encryptSecretKey(publicKey, secretKey);
			return encryptedSecretKey;
		}
		
		catch (Exception e) {
			throw getError(e);
		}
	}
	
	public static void decryptFile(PrivateKey privateKey, byte[] secretKeyBytes, String inputFile, String outputFile)  {
		try {
			String algo = "AES";
			SecretKey secretKey = decryptSecretKey(privateKey, secretKeyBytes);
			Cipher cipher = Cipher.getInstance(algo);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] result = cipher.doFinal(readBytes(inputFile));
			writeBytes(result, outputFile);
		}
		
		catch (Exception e) {
			throw getError(e);
		}
	}
	
	public static String encryptSecretKey(PublicKey publicKey, SecretKey secretKey) throws Exception{
		String algo = "RSA";
		Cipher cipher = Cipher.getInstance(algo);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] result = cipher.doFinal(secretKey.getEncoded());

		return getHex(result);
	}
	
	public static SecretKey decryptSecretKey(PrivateKey privateKey, byte[] secretKeyBytes) throws Exception{
		String algo = "RSA";
		Cipher cipher = Cipher.getInstance(algo);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] result = cipher.doFinal(secretKeyBytes);
		SecretKey secretKey = new SecretKeySpec(result, 0, result.length, "AES");
		return secretKey;
	}
	
	public static RuntimeException getError(Exception e) {
		e.printStackTrace();
		return new RuntimeException(e.getMessage());
	}
	
	public static String createTempFile() {
		try {
			String fileName = LocalDateTime.now().toString() + " +" + getRandomId();
			File tempFile = File.createTempFile(fileName, null);
			return tempFile.getAbsolutePath();
		}
		
		catch (Exception e) {
			throw getError(e);
		}
	}
	
	public static Object readObject(String path){
		try (FileInputStream fis = new FileInputStream(path)) {
			try (ObjectInputStream ois = new ObjectInputStream(fis)) {
				return ois.readObject();
			}
		}
		
		catch (Exception e) {
			throw getError(e);
		}
	}
	public static void writeObject(Object obj, String path) {
		try (FileOutputStream fos = new FileOutputStream(path)) {
			try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
				oos.writeObject(obj);
			}
		}
		
		catch (Exception e) {
			throw getError(e);
		}
	}
}
