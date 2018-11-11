package qu.master.blockchain.gui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import qu.master.blockchain.gui.models.CryptoKeysModel;
import qu.master.blockchain.gui.models.Transaction;
import qu.master.blockchain.gui.models.TransactionInput;
import qu.master.blockchain.gui.models.TransactionOutput;
import qu.master.blockchain.gui.models.Utils;
import qu.master.blockchain.gui.views.MainFrame;
import qu.master.blockchain.gui.views.SwingUtils;
import qu.master.blockchain.gui.views.TransactionPanel;

public class TransactionController extends SwingController {
	
	private TransactionPanel txPanel;
	private Transaction tx;
	private byte[] currentSignBytes;
	
	public TransactionController(MainFrame mainFrame) {
		txPanel = mainFrame.getTransactionPanel();
		tx = new Transaction();
	}
	
	@Override
	public void runController() {
		this.txPanel.getSignButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txPanel.getSignButton().setEnabled(false);
				signTx();
			}
		});
		
		this.txPanel.getVerifyButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txPanel.getVerifyButton().setEnabled(false);
				verifyTx();
			}
		});
	}
	
	private void signTx() {
		try {
			this.updateTx();
			
			currentSignBytes = CryptoKeysModel.signData(tx.getBody());
			txPanel.getSignatureField().setText(Utils.getHexFromBytes(currentSignBytes));
			
			SwingUtils.displayInfoPopup("Success", "Transaction Was Successfully Signed ! ");
		}
		
		catch (Exception e) {
			SwingUtils.displayErrorPopup("Error in Signing", e.getMessage());
		}
		
		finally {
			this.txPanel.getSignButton().setEnabled(true);
		}
	}
	
	private void verifyTx() {
		try {
			String signature = this.txPanel.getSignatureField().getText();
			
			if (signature == null || signature.isEmpty()) {
				SwingUtils.displayWarningPopup("Empty Signature", "This Transaction Has No Associate Signature");
				return;
			}
			
			this.updateTx();
			boolean isSignatureValid = CryptoKeysModel.verifyData(tx.getBody(), currentSignBytes);
			
			if (isSignatureValid) {
				SwingUtils.displayInfoPopup("Valid Signature", "The Transaction Is Valid !");
			}
			
			else {
				SwingUtils.displayErrorPopup("Invalid Signature", "The Transaction is Invalid");
			}
			
		}
		
		catch (Exception e) {
			SwingUtils.displayErrorPopup("Error in Verifying", e.getMessage());
		}
		
		finally {
			this.txPanel.getVerifyButton().setEnabled(true);
		}
	}
	
	private void updateTx() {
		try {
			tx = new Transaction();
			String prevTxHash = this.txPanel.getPrevTxHashField().getText();
			String outputIndex = this.txPanel.getOutputIndexField().getText();
			String value = this.txPanel.getValueField().getText();
			String publicKey = this.txPanel.getPublicKeyField().getText();
			
			tx.addInput(new TransactionInput(prevTxHash, Utils.tryParse(outputIndex, "Output Index Must Be a Number")));
			tx.addOutput(new TransactionOutput(Utils.tryParse(value, "Tx Value Must Be a Number"), publicKey));
		}
		
		catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
