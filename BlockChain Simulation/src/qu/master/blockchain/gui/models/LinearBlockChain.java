package qu.master.blockchain.gui.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

public class LinearBlockChain<E> extends LinkedList<E> implements BlockChain<E> {
	
	private List<Consumer<Integer>> onChainValidationObservers;
	private List<Consumer<Boolean>> onValidateChainObservers;
	
	
	public LinearBlockChain() {
		super(new LinkedBlock(), new LinkedBlock());
		onChainValidationObservers = new ArrayList<>();
		onValidateChainObservers = new ArrayList<>();
	}
	
	//@Override
	public LinkedBlock<E> getTopBlock() {
		return (LinkedBlock<E>) super.getHead().getPrev();
	}
	
	//@Override
	public void appendNewBlock(LinkedBlock<E> newBlock) {
		super.appendNode(newBlock);
	}
	
	@Override
	public boolean validateChain() {
		Stack<LinkedBlock<E>> blocksStack = new Stack<>();
		
		LinkedBlock<E> currentBlock = (LinkedBlock<E>) this.getHead().getPrev();
		while (currentBlock != null) {
			blocksStack.push(currentBlock);
			currentBlock = currentBlock.getPreviousBlock();
		}
		
		return this.validateBlocksStack(blocksStack);
		
	}
	
	
	
	//@Override
	public boolean validateChain(String sequence) {
		LinkedBlock<E> targetBlock = (LinkedBlock<E>) getNodeByKey(sequence);
		return validateChainByBlock(targetBlock);
		
	}
	
	public void addOnChainValidationObserver(Consumer<Integer> action) {
		onChainValidationObservers.add(action);
	}
	
	public void addOnValidateChainObserver(Consumer<Boolean> observer) {
		this.onValidateChainObservers.add(observer);
	}
	
	private void callbackObservers(int firstInvalidBlockIndex) {
		for(Consumer<Integer> action : this.onChainValidationObservers) {
			action.accept(firstInvalidBlockIndex);
		}
	}
	
	private void callbackObservers(boolean isChainValid) {
		for(Consumer<Boolean> observer : onValidateChainObservers) {
			observer.accept(isChainValid);
		}
	}
	
	private boolean validateBlocksStack(Stack<LinkedBlock<E>> blocksStack) {
		boolean finalResult = true;
		boolean foundInvalidBlock = false;
		int firstInvalidBlockIndex = -1;;
		
		if (!blocksStack.isEmpty() && blocksStack.peek().getKey().isEmpty()) {
			return false;
		}
		
		
		int currentIndex = -1;
		
		while (!blocksStack.isEmpty()) {
			currentIndex++;
			LinkedBlock<E> topBlock = blocksStack.pop();
			boolean isBlockValid = topBlock.validateBlock();
			
			if (!isBlockValid && !foundInvalidBlock) {
				firstInvalidBlockIndex = currentIndex;
				foundInvalidBlock = true;
			}
			
			//System.out.println("Block " + topBlock.getKey() + " - " + isBlockValid);
			if (topBlock.getPrev() != null ) {
				//System.out.println("Current " + topBlock.getCurrentHash().substring(4, 12) + " / Prev " + topBlock.getPreviousBlockHash().substring(4, 12) + " / Prev2 " + topBlock.getPreviousBlock().getCurrentHash().substring(4, 12));

			}
			
			else {
				//System.out.println("Current " + topBlock.getCurrentHash().substring(4, 12));
			}
			
			
			finalResult = finalResult && isBlockValid;			
		}
		
		callbackObservers(finalResult);
		return finalResult;
	}
	
	private boolean validateChainByBlock(LinkedBlock<E> currentBlock) {
		boolean foundInvalidBlock = false;
		boolean reachedValidBlock = false;
		boolean result = true;
		
		while (currentBlock != null && !reachedValidBlock) {
			boolean currentBlockStatus = currentBlock.validateBlock();
			result = result && currentBlockStatus;
			reachedValidBlock = currentBlockStatus;
			currentBlock = currentBlock.getPreviousBlock();
		}
		
		
		return result;
	}
	
	//@Override
	public List<LinkedBlock<E>> getAllBlocks() {
		List<LinkedBlock<E>> blocks = new ArrayList<>();
		for(LinkedNode<E> node: super.getNodes()) {
			blocks.add((LinkedBlock<E>) node);
		}
		return blocks;
	}
	
	public int getBlockChainSize() {
		return this.getAllBlocks().size();
	}
	
	public static LinearBlockChain<String> getSampleBlockChain() {
		LinearBlockChain<String> sampleChain = new LinearBlockChain<String>();
		
		LinkedBlock<String> block1 = new LinkedBlock<String>("", "1", "246585", "00002ee7ac1c4ecdcac019f122e4927652abf2f62e1f948ed12d23fb8a3167ea", null, null);
		LinkedBlock<String> block2 = new LinkedBlock<String>("", "2", "397468", "00002a428085db584b22d1b2e4d49e2220d0d4c133b7788edc80ef7fc11d988e", block1, block1.getCurrentHash());
		LinkedBlock<String> block3 = new LinkedBlock<String>("", "3", "601696", "00009c039670c007bb419eb42f4017d1e7761e6306ffa1c3d906867703c2f5fc", block2, block2.getCurrentHash());
		LinkedBlock<String> block4 = new LinkedBlock<String>("", "4", "547666", "0000fac5e3f7fa17746b71daa636d01693df663b14564d506a2d8d412615cd36", block3, block3.getCurrentHash());
		LinkedBlock<String> block5 = new LinkedBlock<String>("", "5", "64415", "0000e8cf029f7219e4a1bd8c92b4d2f8c8d018b4c1504c5b0d20e3eb9cbaa86c", block4, block4.getCurrentHash());
		
		
		sampleChain.appendNewBlock(block1);
		sampleChain.appendNewBlock(block2);
		sampleChain.appendNewBlock(block3);
		sampleChain.appendNewBlock(block4);
		sampleChain.appendNewBlock(block5);
		return sampleChain;
	}
	
	public Block<E> getBlockByIndex(int index) {
		return (Block<E>) super.getNodeByIndex(index);
	}
	
	
}
