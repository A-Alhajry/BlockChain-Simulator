package qu.master.blockchain.gui.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class LinkedBlock<T> extends LinkedNode<T> implements Block<T>{
	private String nonce;
	private String currentHash;
	private String savedHash;
	private LinkedBlock<T> previousBlock;
	private String previousBlockHash;
	private List<Consumer<Boolean>> onValidateObservers = new ArrayList<Consumer<Boolean>>();;
	private List<Consumer<Boolean>> onMiningObservers = new ArrayList<Consumer<Boolean>>();
	private boolean valid;
	private ExecutorService miningService = Executors.newFixedThreadPool(10);
	
	public LinkedBlock() {
		
	}
	
	public LinkedBlock(T data, String sequence, String nonce, LinkedBlock<T> previousBlock) {
		super(sequence, data, previousBlock, null);
		this.nonce = nonce;
		this.previousBlock = previousBlock;
		this.previousBlockHash = previousBlock == null ? "" : previousBlock.getCurrentHash();
		this.savedHash = this.calculateHash();
		validateBlock(false);
		//System.out.println("Start Mining  Block " + sequence);
		//mineBlock();
		//System.out.println("Finish Mining  Block " + sequence);
		
	}
	
	public LinkedBlock(T data, String sequence, String nonce, String hash, LinkedBlock<T> previousBlock, String previousBlockHash) {
		super(sequence, data, previousBlock, null);
		this.nonce = nonce;
		this.previousBlock = previousBlock;
		this.previousBlockHash = previousBlock == null ? "" : previousBlock.getCurrentHash();
		this.currentHash = hash;
		this.savedHash = hash;
		validateBlock(false);
		//System.out.println("Start Mining Linked Block " + sequence);
		//mineBlock();
		//System.out.println("Finish Mining  Linked Block " + sequence);
	}
	
	public static LinkedBlock<String> getValidBlock() {
		LinkedBlock<String> validBlock = new LinkedBlock<String>("", "1", "246585", "00002ee7ac1c4ecdcac019f122e4927652abf2f62e1f948ed12d23fb8a3167ea", null, null);
		return validBlock;
	}
	
	
	public void addValidationObserver(Consumer<Boolean> observer) {
		this.onValidateObservers.add(observer);
	}
	
	public void addOnMiningObserver(Consumer<Boolean> observer) {
		this.onMiningObservers.add(observer);
	}
	
	@Override
	public void mineBlock() {
		if (!validateBlock(false)) {
			boolean isBlockValid = false;
			while (!isBlockValid) {
				this.nonce = "" + getRandomNonce();
				//updateCurrentHash(false);
				this.savedHash = calculateHash();
				isBlockValid = validateBlock();
			}
		}
		
		callBackMiningObservers(true);
	}
	
	public void startMineBlock() {
		miningService.submit(() -> this.mineBlock());
		
		
//		boolean isBlockValid = false;
//		while (!isBlockValid) {
//			this.nonce = "" + getRandomNonce();
//			updateCurrentHash(false);
//			isBlockValid = validateBlock();
//		}
	}
	
	public boolean validateBlock() {
		return validateBlock(true);
	}
	
	public boolean validateBlock(boolean callObservers) {
		
		String actualHash = this.calculateHash();
		
		if (this.getKey().isEmpty()) {
			return false;
		}
		
		if (this.getKey().equals("3")) {
			System.out.println("");
		}
		if (this.getPreviousBlock() != null) {
			if (!this.getPreviousBlock().isValid()) {
				this.valid = false;
			}
			
			else if (!this.getPreviousBlock().getCurrentHash().equals(this.previousBlockHash)) {
				this.valid = false;
			}
			
			else {
				this.valid = actualHash.equals(this.savedHash) && actualHash.startsWith("0000");
			}
		}
		
//		boolean previousBlockStatus = validatePreviousBlock();
//		
//		if (!previousBlockStatus) {
//			this.valid = false;
//		}
		
		else {
			//String inputToHash = getDataToString() + getSequence() + getNonce() + previousBlockHash;
			//String hash = HashingModel.getSha256Hash(inputToHash);
			//this.valid = hash.equals(currentHash) && hash.substring(0, 4).equals("0000");
			this.valid = actualHash.equals(savedHash) && actualHash.startsWith("0000");
		}
		
		//System.out.println(" prev stat " + previousBlockStatus);
		if (callObservers) {
			callbackValidationObservers(this.valid);
		}
		
		return this.valid;
	}
	
	private boolean validatePreviousBlock() {
		if ((previousBlock == null && previousBlockHash != "") || (previousBlock != null && previousBlockHash == "")) {
			return false;
		}
		
		return previousBlock == null || previousBlock.getCurrentHash().equals(previousBlockHash);
		
	}
	
	private void callbackValidationObservers(boolean isValid) {
		for(Consumer<Boolean> observer : onValidateObservers) {
			observer.accept(isValid);
		}
	}
	
	private void callBackMiningObservers(boolean miningSucceeded) {
		for (Consumer<Boolean> observer : this.onMiningObservers) {
			observer.accept(miningSucceeded);;
		}
	}
	
	private int getRandomNonce() {
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(1000000);
	}
	

	private void updateCurrentHash(boolean callValidate) {
		//String inputToHashModel = getData() + getSequence() + getNonce() + getPreviousBlockHash();
		//this.currentHash = HashingModel.getSha256Hash(inputToHashModel);
	    if (callValidate) {
	    	validateBlock();
	    }
	}
	
	public String calculateHash() {
		Object data = super.getData();
		String nonce = this.getNonce();
		String sequence = super.getKey();
		String previousBlockHash = this.getPreviousBlockHash();
		
		StringBuilder inputToHashFunc = new StringBuilder();
		
		inputToHashFunc.append(data == null ? "" : data.toString());
		inputToHashFunc.append(nonce == null ? "" : nonce);
		inputToHashFunc.append(sequence == null ? "" : sequence);
		inputToHashFunc.append(previousBlockHash == null ? "" : previousBlockHash);
		
		return HashingModel.getSha256Hash(inputToHashFunc.toString());
	}
	
	@Override
	public void setData(T data) {
		super.setData(data);
		this.validateBlock();
		//updateCurrentHash(true);
	}
	
	public void setData(T data, boolean callValidate) {
		super.setData(data);
		this.validateBlock();
		//updateCurrentHash(callValidate);
	}
	
	private String getDataToString() {
		return super.getData() == null ? "" : super.getData().toString();
	}
	
	public String getSequence() {
		return super.getKey();
	}
	
	public void setSequence(String sequence) {
		super.setKey(sequence);
		this.validateBlock();
		//updateCurrentHash(true);
	}
	
	public void setSequence(String sequence, boolean callValidate) {
		super.setKey(sequence);
		this.validateBlock();
		//updateCurrentHash(callValidate);
	}
	
	public String getNonce() {
		return this.nonce;
	}
	
	public void setNonce(String nonce) {
		this.nonce = nonce;
		this.validateBlock();
		//updateCurrentHash(true);
	}
	
	public void setNonce(String nonce, boolean callValidate) {
		this.nonce = nonce;
		updateCurrentHash(callValidate);
	}
	
	public String getHash() {
		return getCurrentHash();
	}
	
	public String getCurrentHash() {
		return this.calculateHash();
	}
	
	public void setPreviousBlock(LinkedBlock<T> previousBlock) {
		super.setPrev(previousBlock);
		this.previousBlock = previousBlock;
		this.validateBlock();
	}
	
	public LinkedBlock<T> getPreviousBlock() {
		return (LinkedBlock<T>) super.getPrev();
	}
	
	public void setPreviousBlockHash(String previousBlockHash) {
		this.previousBlockHash = previousBlockHash == null ? "" : previousBlockHash;
		//updateCurrentHash(true)
		this.validateBlock();
;	}
	public String getPreviousBlockHash() {
		return this.previousBlockHash;
	}
	
	public boolean isValid() {
		return this.valid;
	}
	
	
	@Override
	public LinkedNode<T> getNext() {
		throw new IllegalArgumentException("Forward Chaining Is Not Allowed");
	}
	
	@Override
	public void setNext(LinkedNode<T> nextBlock) {
		throw new IllegalArgumentException("Block Does Not Support Forward Chaining");
	}
	
	@Override
	public String toString() {
		return "Block Number = " + this.getSequence();
	}
}
