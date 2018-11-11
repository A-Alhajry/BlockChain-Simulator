package qu.master.blockchain.gui.models;

import java.security.*;

public class KeysPair {
	
	private String publicKey;
	private String privateKey;
	
	public KeysPair() {
		
	}
	
	public KeysPair(KeyPair kPair) {
		this.publicKey = getKeyAsHex(kPair.getPublic());
		this.privateKey = getKeyAsHex(kPair.getPrivate());
	}
	
	public KeysPair(PublicKey publicKey, PrivateKey privateKey) {
		this.publicKey = getKeyAsHex(publicKey);
		this.privateKey = getKeyAsHex(privateKey);
	}
	
	public KeysPair(String publicKey, String privateKey) {
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}
	
	public String getPublicKey() {
		return this.publicKey;
	}
	
	public String getPrivateKey() {
		return this.privateKey;
	}
	
	private String getKeyAsHex(Key key) {
		
		StringBuilder hexBuilder = new StringBuilder();
		
		byte[] keyBytes = key.getEncoded();
		
		for(byte keyByte : keyBytes) {
			hexBuilder.append(String.format("%01X", keyByte));
		}
		
		return hexBuilder.toString();
	}
	
	
}
