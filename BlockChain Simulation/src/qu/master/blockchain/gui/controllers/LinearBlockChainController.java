package qu.master.blockchain.gui.controllers;

import java.util.ArrayList;
import java.util.List;

import qu.master.blockchain.gui.models.Block;
import qu.master.blockchain.gui.models.BlockChain;
import qu.master.blockchain.gui.models.LinearBlockChain;
import qu.master.blockchain.gui.models.LinkedBlock;
import qu.master.blockchain.gui.views.BlockPanel;
import qu.master.blockchain.gui.views.LinearBlockChainPanel;
import qu.master.blockchain.gui.views.MainFrame;

public class LinearBlockChainController extends BlockChainController {
	
	private MainFrame mainFrame;
	private LinearBlockChain<String> blockChain = LinearBlockChain.getSampleBlockChain();
	private LinearBlockChainPanel blockChainPanel;
	
	public LinearBlockChainController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		
	}
	
	@Override
	public void runController() {
		super.setBlockChain(blockChain);
		super.setBlockChainPanel(blockChainPanel);
		blockChain = LinearBlockChain.getSampleBlockChain();
		super.setBlockChain(blockChain);
		List<BlockPanel> blockPanels = new ArrayList<>();
		
		for(int i = 0; i < this.blockChain.getBlockChainSize(); i++) {
			LinkedBlock<String> currentBlock = (LinkedBlock<String>) this.blockChain.getNodeByIndex(i);
			//currentBlock.mineBlock();
			LinkedBlock<String> nextBlock = i == (this.blockChain.getBlockChainSize() - 1) ? null : (LinkedBlock<String>) this.blockChain.getNodeByIndex(i + 1);
			BlockPanel panel = new BlockPanel(currentBlock, true);
			blockPanels.add(panel);
			addBlockObservers(panel, currentBlock);
			addBlockMineButtonClickEvent(panel, currentBlock);
			//System.out.println("Current" + currentBlock + " Next" + nextBlock);
			panel.getNumberTextField().getDocument().addDocumentListener(getTextDocumentListener(panel, currentBlock, nextBlock, null, panel.getNumberTextField()));
			panel.getNonceTextField().getDocument().addDocumentListener(getTextDocumentListener(panel, currentBlock,  nextBlock, null, panel.getNonceTextField()));
			panel.getDataTextArea().getDocument().addDocumentListener(getTextDocumentListener(panel, currentBlock, nextBlock, null, panel.getDataTextArea()));
			//blockChain.addOnChainValidationObserver((firstInvalidIndex) -> inValidateBlocks(firstInvalidIndex));
			blockChain.addOnValidateChainObserver((isChainValid) -> validateChain(isChainValid));
		}
		
		this.blockChainPanel = new LinearBlockChainPanel(blockPanels);
		this.mainFrame.setLinearBlockChainPanel(blockChainPanel);
		//this.blockChainPanel.setScroller();
		
	}
	
	protected void addBlockObservers(BlockPanel panel, Block<String> currentBlock) {
		((LinkedBlock<String>) currentBlock).addValidationObserver((isValid) -> validateChain(isValid));
		currentBlock.addOnMiningObserver((miningSuccedeed) -> onMineBlockComplete(panel, currentBlock, miningSuccedeed)); 
	}
	
	@SuppressWarnings("unchecked")
	protected void validateChain(boolean isChainValid) {
		for(int i = 0; i < this.blockChainPanel.getBlockPanels().size(); i++) {
			LinkedBlock<String> block = (LinkedBlock<String>) this.getBlockChain().getBlockByIndex(i);			
			this.onBlockValidationComplete(this.blockChainPanel.getBlockPanels().get(i), block.validateBlock(false));
		}
	}
	
	protected BlockChain getBlockChain() {
		return this.blockChain;
	}
	
	
	
	
	
	
	
	
}
