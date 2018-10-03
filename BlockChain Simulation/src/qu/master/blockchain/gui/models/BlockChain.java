package qu.master.blockchain.gui.models;

import java.util.List;

public interface BlockChain<T> {
	
	//Block<T> getTopBlock();
	//void appendNewBlock(Block<T> newBlock);
	boolean validateChain();
	Block getBlockByIndex(int index);
	//boolean validateChain(String sequence);
	//List<Block<T>> getAllBlocks();
}
