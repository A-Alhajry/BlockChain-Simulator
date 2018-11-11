package qu.master.blockchain.gui.models;

public class Utils {
	
	public static String getHexFromBytes(byte[] bytes) {
		StringBuilder hexBuilder = new StringBuilder();
		
		for(byte inputByte : bytes) {
			hexBuilder.append(String.format("%01X", inputByte));
		}
		
		return hexBuilder.toString();
	}
	
	public static byte[] getBytesFromHex(String hexString) {
		
		if (hexString == null || hexString.isEmpty() || hexString.length() % 2 != 0) {
			throw new RuntimeException("Provided Hex Is Not Valid ");
		}
		
		byte[] bytes = new byte[hexString.length() / 2];
		
		for(int i = 0; i < bytes.length; i++) {
			
			String hex = hexString.substring(i * 2, i * 2 + 2);
			bytes[i] = Byte.parseByte(hex,16);
		}
		
		return bytes;
	}
	
	public static int tryParse(String input, String errorMessage) {
		try {
			return Integer.parseInt(input);
		}
		
		catch (Exception e) {
			throw new RuntimeException(errorMessage);
		}
	}
}