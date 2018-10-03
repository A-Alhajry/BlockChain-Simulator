package qu.master.blockchain.gui.controllers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import qu.master.blockchain.gui.Constants;
import qu.master.blockchain.gui.models.BlockChain;
import qu.master.blockchain.gui.models.TreeBlock;
import qu.master.blockchain.gui.models.TreeBlockChain;
import qu.master.blockchain.gui.views.BlockPanel;
import qu.master.blockchain.gui.views.MainFrame;
import qu.master.blockchain.gui.views.TreeBlockChainPanel;

public class TreeBlockChainController extends BlockChainController {

	private MainFrame mainFrame;
	private TreeBlockChain<String> blockChain;
	private TreeBlockChainPanel blockChainPanel;
	
	
	public TreeBlockChainController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.blockChainPanel = mainFrame.getTreeBlockChainPanel();
		this.blockChain = TreeBlockChain.getSampleTree();
	}
	
	@Override
	public void runController() {
		super.setBlockChain(blockChain);
		super.setBlockChainPanel(blockChainPanel);
		List<BlockPanel> blockPanels = new ArrayList<>();
		
		for (int i = 0; i < blockChain.getLastNodeOrder(); i++) {
			TreeBlock<String> currentBlock = blockChain.getBlockByIndex(i);
			TreeBlock<String> parentBlock = null;
			
			if (currentBlock.getOrder() > 1) {
				if (currentBlock.isRightTree()) {
					parentBlock = (TreeBlock<String>) blockChain.getNodeByOrder((int) (currentBlock.getOrder() - 1) / 2 );
				}
				
				else {
					parentBlock = (TreeBlock<String>) blockChain.getNodeByOrder((int) (currentBlock.getOrder()) / 2);
				}
			}
			TreeBlock<String> rightBlock = currentBlock.getRightBlock();
			TreeBlock<String> leftBlock = currentBlock.getLeftBlock();
			BlockPanel panel = new BlockPanel(currentBlock, false);
			panel.setBackground(Color.decode(Constants.ValidBlockColor));
			blockPanels.add(panel);
			addBlockObservers(panel, currentBlock);
			addBlockMineButtonClickEvent(panel, currentBlock);
			panel.getNumberTextField().getDocument().addDocumentListener(getTextDocumentListener(panel, currentBlock, null, parentBlock, panel.getNumberTextField()));
			panel.getNonceTextField().getDocument().addDocumentListener(getTextDocumentListener(panel, currentBlock, null, parentBlock, panel.getNonceTextField()));
			panel.getDataTextArea().getDocument().addDocumentListener(getTextDocumentListener(panel, currentBlock, null, parentBlock, panel.getDataTextArea()));
			currentBlock.addValidationObserver((isValid) -> this.validateChain(panel, currentBlock, isValid));
			
		}
		
		//this.blockChain.addValidationObserver((isChainValid) -> validateChain(isChainValid));
		this.blockChainPanel = new TreeBlockChainPanel(blockPanels);
		this.mainFrame.setTreeBlockChainPanel(this.blockChainPanel);
	}
	
	protected void validateChain(BlockPanel panel, TreeBlock<String> block, boolean isValid) {
//		for(int i = 0; i < this.blockChainPanel.getBlockPanels().size(); i++) {
//			BlockPanel panel = this.blockChainPanel.getBlockPanels().get(i);
//			TreeBlock<String> block = this.blockChain.getBlockByIndex(i);
//			this.onBlockValidationComplete(panel, block.isValid());
//		}
		
		List<BlockPanel> parents = new ArrayList<>();
		
		
		if (block.getParentOrder() > 0) {
			BlockPanel currentParent = panel;
			int currentParentOrder = block.getParentOrder();
			while(currentParentOrder > 1) {
				currentParentOrder = ((TreeBlock<String>) currentParent.getBlock()).getParentOrder();
				currentParent = this.blockChainPanel.getBlockPanels().get(currentParentOrder - 1);
				parents.add(currentParent);
				
				
			}
		}
		
		
		
		for(int i = 0; i < block.getOrder(); i++) {
			BlockPanel currentPanel = this.blockChainPanel.getBlockPanels().get(i);
			TreeBlock<String> treeBlock = this.blockChain.getBlockByIndex(i);
			int currentOrder = block.getOrder();
			
			//if ()
			//this.onBlockValidationComplete(currentPanel, ((TreeBlock<String>) currentPanel.getBlock()).validateBlock(false));
		}
		
		this.onBlockValidationComplete(panel, block.validateBlock(false));
		for(BlockPanel parentPanel : parents) {
			
			if (!block.isValid()) {
				this.onBlockValidationComplete(parentPanel, false);
			}
			
			else {
				this.onBlockValidationComplete(parentPanel, ((TreeBlock<String>) parentPanel.getBlock()).validateBlock(false));
			}
		}
		
	}
		
	
	public BlockChain getBlockChain() {
		return this.blockChain;
	}
}
