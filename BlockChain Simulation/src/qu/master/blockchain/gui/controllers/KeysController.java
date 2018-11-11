package qu.master.blockchain.gui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.NoSuchFileException;

import qu.master.blockchain.gui.models.CryptoKeysModel;
import qu.master.blockchain.gui.models.KeysPair;
import qu.master.blockchain.gui.views.KeysPanel;
import qu.master.blockchain.gui.views.MainFrame;
import qu.master.blockchain.gui.views.SwingUtils;

public class KeysController extends SwingController {
	
	private KeysPanel keysPanel;
	
	public KeysController(MainFrame mainFrame) {
		this.keysPanel = mainFrame.getKeysPanel();
	}
	
	@Override
	public void runController() {
		this.keysPanel.getGenerateButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				keysPanel.getGenerateButton().setEnabled(false);
				generateKeys();
			}
		});
		
		this.keysPanel.getSaveButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveKeys();
			}
		});
		
		this.keysPanel.getLoadButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadKeys();
			}
		});
	}
	
	private void generateKeys() {
		KeysPair keysPair = CryptoKeysModel.generateKeysPair();
		this.keysPanel.getPublicKeyField().setText(keysPair.getPublicKey());
		this.keysPanel.getPrivateKeyField().setText(keysPair.getPrivateKey());
		this.keysPanel.getGenerateButton().setEnabled(true);
		SwingUtils.displayInfoPopup("Success", "Key Pair Has Been Successfully Generated");
	}
	
	private void saveKeys() {
		try {
			String publicKey = this.keysPanel.getPublicKeyField().getText();
			String privateKey = this.keysPanel.getPrivateKeyField().getText();
			
			if (publicKey == null || privateKey == null) {
				SwingUtils.displayWarningPopup("Invalid", "Please Generate Valid Keys");
				return;
			}
			String password = SwingUtils.showInputDialog("Please Choose a Password: ");
			if (password != null) {
				CryptoKeysModel.saveKeys(password);
				SwingUtils.displayInfoPopup("Success", "Keys Pair was Saved ! ");
			}
		}
		
		catch (Exception e) {
			SwingUtils.displayErrorPopup("Error", "Error In Saving Keys");
			e.printStackTrace();
		}
	}
	
	private void loadKeys() {
		try {
			String password = SwingUtils.showInputDialog("Please Enter Your Password:");
			if (password != null) {
				KeysPair keys = CryptoKeysModel.loadKeys(password);
				this.keysPanel.getPublicKeyField().setText(keys.getPublicKey());
				this.keysPanel.getPrivateKeyField().setText(keys.getPrivateKey());
			}
		}
		
		catch (Exception e) {
			if (e.getMessage().contains("PKCS8EncodedKeySpec")) {
				SwingUtils.displayErrorPopup("Invalid Password", "The Password Is Not Correct! " );
			}
			
			else if (e instanceof NoSuchFileException){
				SwingUtils.displayErrorPopup("No Keys", "No Keys Found! ");
			}
			
			else {
				SwingUtils.displayErrorPopup("Error", "Error In Loading Keys");
				e.printStackTrace();
			}
			
		}
	}
}
