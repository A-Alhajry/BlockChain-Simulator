package qu.master.blockchain.gui.models;

public class TransactionOutput {
	
	private int value;
	private String publicKey;
	
	public TransactionOutput(int value, String publicKey) {
		this.value = value;
		this.publicKey = publicKey;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public String getPublicKey() {
		return this.publicKey;
	}
	
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
}
