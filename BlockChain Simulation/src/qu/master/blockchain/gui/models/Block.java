package qu.master.blockchain.gui.models;

import java.util.function.Consumer;

public interface Block<T> {
	void mineBlock();
	boolean validateBlock();
	boolean isValid();
	void addOnMiningObserver(Consumer<Boolean> observer);
	void startMineBlock();
	
	T getData();
	void setData(T data);
	String getHash();
	String getNonce();
	void setNonce(String nonce);
	String getSequence();
	void setSequence(String sequence);
	
	public default String calculateHash() {
		return null;
	}
}
