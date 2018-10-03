package qu.master.blockchain.gui.models;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashingModel {
	
	
	public static String getSha256Hash(String input){
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] inputBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			return bytesToHexString(inputBytes);
		}
		
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}
	
	private static String bytesToHexString(byte[] bytes) {
		
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0;  i < bytes.length; i++) {
			String hex = String.format("%02X", bytes[i]);
			builder.append(hex.toLowerCase());
		}
		
		return builder.toString();
	}
	
	
}
