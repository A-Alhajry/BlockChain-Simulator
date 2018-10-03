package qu.master.blockchain.gui.controllers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import qu.master.blockchain.gui.Constants;
import qu.master.blockchain.gui.models.Block;
import qu.master.blockchain.gui.models.BlockChain;
import qu.master.blockchain.gui.models.LinkedBlock;
import qu.master.blockchain.gui.models.TreeBlock;
import qu.master.blockchain.gui.views.BlockChainPanel;
import qu.master.blockchain.gui.views.BlockPanel;

public abstract class BlockChainController extends SwingController {
	
	private BlockChain<String> blockChain;
	private BlockChainPanel blockChainPanel;
	
	protected void setBlockChain(BlockChain<String> blockChain) {
		this.blockChain = blockChain;
	}
	
	protected void setBlockChainPanel(BlockChainPanel blockChainPanel) {
		this.blockChainPanel = blockChainPanel;
	}
	
	
	protected void addBlockObservers(BlockPanel panel, Block<String> currentBlock) {
		//currentBlock.addValidationObserver((isValid) -> onBlockValidationComplete(panel, previousBlock, currentBlock, isValid));
		currentBlock.addOnMiningObserver((miningSuccedeed) -> onMineBlockComplete(panel, currentBlock, miningSuccedeed)); 
	}
	
	protected void addBlockMineButtonClickEvent(BlockPanel panel, Block<String> block) {
		panel.getMineButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				validateChain(panel, block);
			}
		});
	}
	
	protected DocumentListener getTextDocumentListener(BlockPanel panel, Block<String> currentBlock, LinkedBlock<String> nextBlock, TreeBlock<String> parentBlock, JTextComponent source) {
		return new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				onBlockPanelTextListener(panel, currentBlock, nextBlock, parentBlock, source);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				onBlockPanelTextListener(panel, currentBlock, nextBlock, parentBlock, source);
			}
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				onBlockPanelTextListener(panel, currentBlock, nextBlock, parentBlock, source);
			}
		};
	}
	
	protected void onBlockValidation(BlockPanel panel, Block block, boolean isValid) {
	}
	
	protected void onBlockPanelTextListener(BlockPanel panel, Block<String> currentBlock, LinkedBlock<String> nextBlock, TreeBlock<String> parentBlock, JTextComponent source) {
		String text = source.getText();
				
		if (source == panel.getNumberTextField()) {
			currentBlock.setSequence(text);
		}
		
		else if (source == panel.getNonceTextField()) {
			currentBlock.setNonce(text);
		}
		
		else if (source == panel.getDataTextArea()) {
			currentBlock.setData(text);
		}
		
		else {
			return;
		}
		
		if (currentBlock instanceof LinkedBlock) {
			if (nextBlock != null) {
				nextBlock.setPreviousBlockHash(currentBlock.getHash());
			}
			
			LinkedBlock<String> linkedBlock = (LinkedBlock<String>) currentBlock;
			//linkedBlock.validateBlock(false);
		}
		
		else {
			if (parentBlock != null) {
				TreeBlock<String> block = (TreeBlock<String>) currentBlock;
				
				if (block.isRightTree()) {
					parentBlock.setRightBlockHash(block.getHash());
				}
				
				else {
					parentBlock.setLeftBlockHash(block.getHash());
				}
			}
			
			//blockChain.validateChain();
			currentBlock.validateBlock();

		}
		
		
		
	}
	
	protected void validateChain(BlockPanel panel, Block block) {
		panel.getMineButton().setEnabled(false);
		panel.getMineButton().setIcon(panel.getLoadingIcon());
		panel.getNumberTextField().setEnabled(false);
		panel.getNonceTextField().setEnabled(false);
		panel.getDataTextArea().setEnabled(false);
		block.startMineBlock();
	}
	
	
	
	protected abstract BlockChain getBlockChain();
	
	
//	protected void inValidateBlocks(int firstInvalidBlockIndex) {
//		
//		if (firstInvalidBlockIndex > 0 ) {
//			for(int i = firstInvalidBlockIndex; i < this.blockChain.getBlockChainSize(); i++) {
//				this.onBlockValidationComplete(this.blockChainPanel.getBlockPanels().get(i), false);
//				
//			}
//		}
//		
//	}
	protected void onBlockValidationComplete(BlockPanel panel, boolean isValid) {
		
		if (isValid) {
			panel.setBackground(Color.decode(Constants.ValidBlockColor));
		}
		
		else {
			panel.setBackground(Color.decode(Constants.InvalidBlockColor));
		}
	}
	protected void onMineBlockComplete(BlockPanel panel, Block block, boolean isValid) {
		panel.getMineButton().setEnabled(true);
		panel.getMineButton().setIcon(null);
		panel.getNumberTextField().setEnabled(true);
		panel.getNonceTextField().setEnabled(true);
		panel.getDataTextArea().setEnabled(true);
		
		panel.getNonceTextField().setText(block.getNonce().toString());
		panel.getHashTextField().setText(block.getHash());
		
		if (block instanceof LinkedBlock) {
			LinkedBlock actualBlock = (LinkedBlock) block;
			if (actualBlock.getPreviousBlock() != null) {
				panel.getPrevTextField().setText(actualBlock.getPreviousBlockHash());
			}
		}
		
		else {
			TreeBlock actualBlock = (TreeBlock) block;
			
			if (actualBlock.getRightBlock() != null) {
				panel.getRightTextField().setText(actualBlock.getRightBlock().getHash());
			}
			if (actualBlock.getLeftBlock() != null) {
				panel.getLeftTextField().setText(actualBlock.getLeftBlock().getHash());
			}
		}
		
	}
}
