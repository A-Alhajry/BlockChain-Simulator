package qu.master.blockchain.documentsattestation.models.beans;

import java.io.File;
import java.security.KeyPair;
import java.security.PublicKey;

import qu.master.blockchain.documentsattestation.AppUtils;
import qu.master.blockchain.documentsattestation.Logger;

public class CryptoModel {
	
	private String publicKeyFile = "KeysPair" + File.separator;
	private String privateKeyFile = "KeysPair" + File.separator;
	
	private static final String keyGenAlgo = "RSA";
	private static final String prvEncAlgo = "PBEWithSHA1AndDESede";
	private static final String signAlgo = "SHA256withRSA";
	private static final int keySize = 2084;
	
	private KeyPair keyPair;
	
	public CryptoModel(String userId) {
		setKeysPaths(userId);
		byte[] keyBytes = AppUtils.readBytes(publicKeyFile);
		PublicKey pubKey = AppUtils.bytesToPublicKey(keyGenAlgo, keyBytes);
		this.keyPair = new KeyPair(pubKey, null);
	}
	
	public CryptoModel(String password, String userId) {
		setKeysPaths(userId);
		this.keyPair = AppUtils.loadKeyPair(password, publicKeyFile, privateKeyFile, keyGenAlgo, keyGenAlgo);

	}
	
	private void setKeysPaths(String userId) {
		publicKeyFile += userId + ".pub";
		privateKeyFile += userId + ".der";
	}
	
	public static void generateKeyPair(String password, String userId) {
		
		KeyPair keyPair = AppUtils.generatePair(keySize, keyGenAlgo);
		AppUtils.saveKeyPair(keyPair, password, "KeysPair" + File.separator + userId + ".pub", "KeysPair" + File.separator + userId + ".der", prvEncAlgo);
		
	}
	
	public String signData(byte[] data) {
		byte[] result = AppUtils.signData(data, this.keyPair.getPrivate(), signAlgo);
		return AppUtils.getHex(result);
	}
	
	public boolean verifyData(byte[] data) {
		return AppUtils.verifyData(data, keyPair.getPublic(), signAlgo);
	}
	
	public String encryptData(byte[] data, String inputFile, String outputFile) {
		//AppUtils.writeBytes(data, inputFile);
		return AppUtils.encryptFile(this.keyPair.getPublic(), inputFile, outputFile);
	}
	
	public byte[] decryptData(byte[] data, String key, String inputFile, String outputFile) {
		byte[] keyBytes = AppUtils.parseHex(key);
		AppUtils.decryptFile(this.keyPair.getPrivate(), keyBytes, inputFile, outputFile);
		return AppUtils.readBytes(outputFile);
	}
	
}
