package qu.master.blockchain.gui.controllers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import qu.master.blockchain.gui.Constants;
import qu.master.blockchain.gui.models.LinkedBlock;
import qu.master.blockchain.gui.views.BlockPanel;
import qu.master.blockchain.gui.views.MainFrame;

public class BlockController extends SwingController{
	
	private LinkedBlock<String> currentBlock = LinkedBlock.getValidBlock();
	private BlockPanel blockPanel = new BlockPanel(currentBlock, false);
	
	public BlockController(MainFrame mainFrame) {
		//validateBlock(currentBlock.isValid());
		this.blockPanel = mainFrame.getBlockPanel();
		this.blockPanel.setBackground(Color.decode(Constants.ValidBlockColor));
		Consumer<Boolean> blockValidationObserver = (isValid) -> validateBlock(isValid);
		Consumer<Boolean> blockMiningObserver = (miningSucceeded) -> onBlockMiningComplete();
		currentBlock.addValidationObserver((blockValidationObserver));
		currentBlock.addOnMiningObserver(blockMiningObserver);
		addTextChangeListener(blockPanel.getNumberTextField());
		addTextChangeListener(blockPanel.getNonceTextField());
		addTextChangeListener(blockPanel.getDataTextArea());
		addMineButtonClickListener();
	}
	
	@Override
	public void runController() {
		
	}
	
	private void addMineButtonClickListener() {
		blockPanel.getMineButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//blockPanel.getMineButton().setEnabled(false);
				blockPanel.getMineButton().setIcon(blockPanel.getLoadingIcon());
				blockPanel.getNumberTextField().setEnabled(false);
				blockPanel.getNonceTextField().setEnabled(false);
				blockPanel.getDataTextArea().setEnabled(false);
				//blockPanel.getParentFrame().setOpacity(0.5f);
				//blockPanel.setOpacity(0.5f);
				
				currentBlock.startMineBlock();
				
				
			}
		});
	}
	
	private void onBlockMiningComplete() {
		blockPanel.getMineButton().setEnabled(true);
		blockPanel.getMineButton().setIcon(null);
		blockPanel.getMineButton().repaint();
		blockPanel.getNumberTextField().setEnabled(true);
		blockPanel.getNonceTextField().setEnabled(true);
		blockPanel.getDataTextArea().setEnabled(true);
		
		blockPanel.getNonceTextField().setText(currentBlock.getNonce());
		blockPanel.getHashTextField().setText(currentBlock.getCurrentHash());
		//blockPanel.getParentFrame().setOpacity(1f);
		//blockPanel.setOpacity(1f);
	}
	
	private void addTextChangeListener(JTextComponent control) {
		control.getDocument().addDocumentListener(new DocumentListener() {
			
			public void changedUpdate(DocumentEvent event) {
				updateBlock(control);
			}
			
			public void insertUpdate(DocumentEvent event) {
				updateBlock(control);
			}
			
			public void removeUpdate(DocumentEvent event) {
				updateBlock(control);
			}
		});
	}
	
	private void updateBlock(JTextComponent source) {
		if (source == blockPanel.getNumberTextField()) {
			currentBlock.setSequence(source.getText());
		}
		
		else if (source == blockPanel.getNonceTextField()) {
			currentBlock.setNonce(source.getText());
		}
		
		else if (source == blockPanel.getDataTextArea()) {
			currentBlock.setData(source.getText());
		}
		
		else {
			return;
		}
		
	}
	
	private void validateBlock(boolean isBlockValid) {
		if (isBlockValid) {
			setFrameBackgroundColor(Constants.ValidBlockColor);
		}
		
		else {
			setFrameBackgroundColor(Constants.InvalidBlockColor);
		}
	}
	private void setFrameBackgroundColor(String hexColor) {
		this.blockPanel.setBackground(Color.decode(hexColor));
	}
}
