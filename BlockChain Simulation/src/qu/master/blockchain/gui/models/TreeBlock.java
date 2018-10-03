package qu.master.blockchain.gui.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class TreeBlock<T> extends TreeNode<T> implements Block<T>{

	private int order;
	private String  nonce;
	private boolean isValid;
	private String savedHash;
	private String rightBlockHash;
	private String leftBlockHash;
	private List<Consumer<Boolean>> validationObservers = new ArrayList<>();
	private List<Consumer<Boolean>> miningObservers = new ArrayList<>();
	private ExecutorService miningService = Executors.newFixedThreadPool(10);
	
	
	public TreeBlock() {
		super();
	}
	
	public TreeBlock(T data, String nonce, String hash, int order, int level, TreeBlock<T> right, TreeBlock<T> left, String rightBlockHash, String leftBlockHash) {
		super(data, right, left);
		this.nonce = nonce;
		super.setLevel(level);
		this.savedHash = hash;
		this.order = order;
		this.rightBlockHash = rightBlockHash;
		this.leftBlockHash = leftBlockHash;
		setSequence("" + order);
		//System.out.println("Start Mining Tree  Block " + order);
		mineBlock();
		//System.out.println("Finish Mining  Tree Block " + order);
//		this.validateBlock(false);
	}
	
	public boolean validateBlock(boolean callObservers) {
		String actualHash = calculateHash();
		TreeBlock<T> rightBlock = getRightBlock();
		TreeBlock<T> leftBlock = getLeftBlock();
		boolean isRightHashValid = true;
		boolean isLeftHashValid = true;
		
		if (rightBlock != null || leftBlock != null) {
			if (rightBlock != null) {
				if (!rightBlock.isValid()) {
					//this.isValid = false;
					isRightHashValid = false;
				}
				
				else if (!rightBlock.getHash().equals(rightBlockHash)) {
					//this.isValid = false;
					isRightHashValid = false;
				}
				
				else {
					//isRightHashValid = actualHash.equals(this.savedHash) && actualHash.startsWith("0000");
				}
			}
			
			else {
				if (!leftBlock.isValid()) {
					//this.isValid = false;
					isLeftHashValid = false;
				}
				
				else if (!leftBlock.getHash().equals(leftBlockHash)) {
					//this.isValid = false;
					isLeftHashValid = false;
				}
				
				else {
					//return actualHash.equals(savedHash) && actualHash.startsWith("0000");
				}
			}
			
		}
		
		this.isValid = actualHash.equals(this.savedHash) && isRightHashValid && isLeftHashValid && actualHash.startsWith("0000");
		
		if (callObservers) {
			callValidationObservers(this.isValid);

		}
		return isValid;
	}
	
	@Override
	public boolean validateBlock() {
		
		return validateBlock(true);
	}
	
	@Override
	public void mineBlock() {
		if (!validateBlock()) {
			boolean isValid = false;
			while(!isValid) {
				Random random = new Random();
				setNonce(random.nextInt(1000000) + "");
				this.savedHash = this.calculateHash();
				isValid = validateBlock();
			}
		}

		callMiningObservers(true);
	}
	
	public void startMineBlock() {
		miningService.submit(() -> mineBlock());
	}
	
	public String calculateHash() {
		Object data = super.getData();
		String nonce = this.getNonce();
		String sequence = getSequence();
		String rightHash = this.rightBlockHash;
		String leftHash = this.leftBlockHash;
		
		StringBuilder inputToHashFunc = new StringBuilder();
		inputToHashFunc.append(data == null ? "" : data.toString());
		inputToHashFunc.append(nonce == null ? "" : nonce);
		inputToHashFunc.append(sequence == null ? "" : sequence);
		inputToHashFunc.append(rightHash == null ? "" : rightHash);
		inputToHashFunc.append(leftHash == null ? "" : leftHash);
		
		return HashingModel.getSha256Hash(inputToHashFunc.toString());
	}
	
	public static <E> List<TreeBlock<E>> getBlocksByLevel(List<TreeBlock<E>> blocks, int level) {
		Predicate<TreeBlock<E>> predicate = (block) -> block.getLevel() == level;
		return blocks.stream().filter(predicate).collect(Collectors.toList());
	}
	
	public void addValidationObserver(Consumer<Boolean> observer) {
		this.validationObservers.add(observer);
	}
	
	private void callValidationObservers(boolean isValid) {
		for(Consumer<Boolean> observer  : validationObservers) {
			observer.accept(isValid);
		}
	}
	
	public void addOnMiningObserver(Consumer<Boolean> observer) {
		this.miningObservers.add(observer);
	}
	
	public void callMiningObservers(boolean isValid) {
		for(Consumer<Boolean> observer : miningObservers) {
			observer.accept(isValid);
		}
	}
	
	@Override
	public String toString() {
		return " TreeBlock Order " + this.getOrder();
	}
	
	@Override
	public void setData(T data) {
		super.setData(data);
		this.validateBlock(false);
	}
	
	public int getOrder() {
		return this.order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	@Override
	public boolean isValid() {
		return this.isValid;
	}
	
	@Override
	public String getHash() {
		return calculateHash();
	}
	
	public String getSequence() {
		return super.getKey();
	}
	
	public void setSequence(String sequence) {
		super.setKey(sequence);
		this.validateBlock(false);
	}
	
	public String getNonce() {
		return this.nonce;
	}
	
	public void setNonce(String nonce) {
		this.nonce = nonce;
		this.validateBlock(false);
	}
	
	public TreeBlock<T> getRightBlock() {
		return (TreeBlock<T>) super.getRight();
	}
	
	public void setRightBlock(TreeBlock<T> rightBlock) {
		super.setRight(rightBlock);
	}
	
	public TreeBlock<T> getLeftBlock() {
		return (TreeBlock<T>) super.getLeft();
	}
	
	public void setLeftBlock(TreeNode<T> leftBlock) {
		super.setLeft(leftBlock);
	}
	
	public String getRightBlockHash() {
		return this.rightBlockHash;
	}
	
	public void setRightBlockHash(String hash) {
		this.rightBlockHash = hash;
	}
	public String getLeftBlockHash() {
		return this.leftBlockHash;
	}
	
	public void setLeftBlockHash(String hash) {
		this.leftBlockHash = hash;
	}
	public boolean isRightTree() {
		return this.order == 1 ? false : this.order % 2 != 0;
	}
	
	public boolean isLeftTree() {
		return this.order == 1 ? false : this.order % 2 == 0;
	}
	
	public int getParentOrder() {
		if (this.order == 1) {
			return 0;
		}
		
		else if (isRightTree()) {
			return (this.order - 1) / 2;
		}
		
		else {
			return this.order / 2;
		}
	}
}
