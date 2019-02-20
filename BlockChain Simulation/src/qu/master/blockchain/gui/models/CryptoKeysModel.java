package qu.master.blockchain.gui.models;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class CryptoKeysModel {
	
	private static final String CryptAlgorithm = "RSA";
	private static final String SignAlgorithm = "SHA256withRSA";
	private static final int KeySize = 2084;
	
	private static final String publicKeyFile = "public.pub";
	private static final String privateKeyFile = "private.der";
	
	private static final String pvtKeyEncAlgo = "PBEWithSHA1AndDESede";
	
	private static KeyPair keyPair;
	private static KeysPair myKeys;
	
	public static KeysPair generateKeysPair() {
		
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(CryptAlgorithm);
			kpg.initialize(KeySize);
			keyPair =  kpg.generateKeyPair();
			myKeys = new KeysPair(keyPair);
			return myKeys;
		}
		
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static KeysPair getSavedKeysPair() {
		return myKeys;
	}
	
	public static byte[] signData(String data) {
		
		if (keyPair == null) {
			throw new RuntimeException(" No Key Pair Found ");
		}
		
		try {
			Signature digitalSign = Signature.getInstance(SignAlgorithm);
			digitalSign.initSign(keyPair.getPrivate());
			
			byte[] bytes = data.getBytes();
			for(byte dataByte : bytes) {
				digitalSign.update(dataByte);
			}
			
			return digitalSign.sign();
		}
		
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static boolean verifyData(String data, byte[] signature) {
		
		if (keyPair == null) {
			throw new RuntimeException(" No Key Pair Found ");
		}
		try {
			Signature digitalSign = Signature.getInstance(SignAlgorithm);
			digitalSign.initVerify(keyPair.getPublic());
			
			byte[] bytes = data.getBytes();
			for(byte dataByte : bytes) {
				digitalSign.update(dataByte);
			}
			
			return digitalSign.verify(signature);
		}
		
		catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static void saveKeys(String password) throws Exception {
		writeBytes(keyPair.getPublic().getEncoded(), publicKeyFile);
		savePrivateKey(password);
	}
	public static KeysPair loadKeys(String password) throws Exception {
		byte[] publicKeyBytes = readBytes(publicKeyFile);
		PublicKey publicKey = readPublicKey(publicKeyBytes);
		PrivateKey privateKey = loadPrivateKey(password);
		myKeys = new KeysPair(publicKey, privateKey);
		keyPair = new KeyPair(publicKey, privateKey);
		return myKeys;
	}
	
	private static void savePrivateKey(String password) throws Exception {
		int iterationsCount = 20;
		byte[] salt = new byte[8];
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);
		
		PBEParameterSpec paramSpec = new PBEParameterSpec(salt, iterationsCount);
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(pvtKeyEncAlgo);
		SecretKey secretKey = keyFactory.generateSecret(keySpec);
		
		Cipher keyCipher = Cipher.getInstance(pvtKeyEncAlgo);
		keyCipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
		byte[] cipherText = keyCipher.doFinal(keyPair.getPrivate().getEncoded());
		
		AlgorithmParameters algoParams = AlgorithmParameters.getInstance(pvtKeyEncAlgo);
		algoParams.init(paramSpec);
		EncryptedPrivateKeyInfo encryptedKey = new EncryptedPrivateKeyInfo(algoParams, cipherText);
		writeBytes(encryptedKey.getEncoded(), privateKeyFile);
		
		
	}
	
	private static PrivateKey loadPrivateKey(String password) throws Exception {
		byte[] keyBytes = readBytes(privateKeyFile);
		EncryptedPrivateKeyInfo keyInfo = new EncryptedPrivateKeyInfo(keyBytes);
		
		Cipher keyCipher = Cipher.getInstance(keyInfo.getAlgName());
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(keyInfo.getAlgName());
		SecretKey secretKey = keyFactory.generateSecret(keySpec);
		AlgorithmParameters algoParams = keyInfo.getAlgParameters();
		keyCipher.init(Cipher.DECRYPT_MODE, secretKey, algoParams);
		KeySpec pksKeySpec = keyInfo.getKeySpec(keyCipher);
		KeyFactory pksKeyFactory = KeyFactory.getInstance(CryptAlgorithm);
		return pksKeyFactory.generatePrivate(pksKeySpec);
	}
	
	private static byte[] readBytes(String path) throws Exception {
		Path filePath = Paths.get(path);
		return Files.readAllBytes(filePath);
	}
	
	private static void writeBytes(byte[] bytes, String path) throws Exception {
		FileOutputStream fos = null;
		try {
			File outputFile = new File(path);
			fos = new FileOutputStream(outputFile);
			fos.write(bytes, 0, bytes.length);
		}
		
		finally {
			if (fos != null) {
				fos.close();
			}
		}
	}
	private static PublicKey readPublicKey(byte[] bytes) throws Exception {
		X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);
		KeyFactory kf = KeyFactory.getInstance(CryptAlgorithm);
		return kf.generatePublic(ks);
	}
	
	private static PrivateKey readPrivateKey(byte[] bytes) throws Exception{
		PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
		KeyFactory kf = KeyFactory.getInstance(CryptAlgorithm);
		return kf.generatePrivate(ks);
	}
	
}
