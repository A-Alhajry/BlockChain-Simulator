package qu.master.blockchain.gui.models;

public class TransactionInput {
	
	private String prevTxHash;
	private int outputIndex;
	private String signature;
	
	public TransactionInput(String prevTxHash, int outputIndex) {
		this.prevTxHash = prevTxHash;
		this.outputIndex = outputIndex;
	}
	
	public String getPrevTxHash() {
		return this.prevTxHash;
	}
	
	public void setPrevTxHash(String prevTxHash) {
		this.prevTxHash = prevTxHash;
	}
	
	public int getOutputIndex() {
		return this.outputIndex;
	}
	
	public void setOutputIndex(int outputIndex) {
		this.outputIndex = outputIndex;
	}
	
	public String getSignature() {
		return this.signature;
	}
	
	public void setSignature(String signature) {
		this.signature = signature;
	}
}
