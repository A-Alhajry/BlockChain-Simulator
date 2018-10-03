package qu.master.blockchain.gui.controllers;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import qu.master.blockchain.gui.models.*;
import qu.master.blockchain.gui.views.*;


public class HashController extends SwingController {
	
	private HashPanel hashPanel;
	private JTextArea inputView;
	private JLabel resultView;
	
	public HashController(HashPanel hashPanel) {
		this.hashPanel = hashPanel;
		this.inputView = hashPanel.getInputField();
		this.resultView = hashPanel.getResultField();
	}
	
	@Override
	public void runController() {
		//hashPanel.setVisible(true);
		addTextChangeListener(inputView);
	}
	
	private void addTextChangeListener(JTextArea field) {
		field.getDocument().addDocumentListener(new DocumentListener() {
			
			public void changedUpdate(DocumentEvent event){
				
				updateResult(inputView.getText());
			}
			
			public void removeUpdate(DocumentEvent event) {
				updateResult(inputView.getText());
			}
			
			public void insertUpdate(DocumentEvent event) {
				updateResult(inputView.getText());
			}
		});
	}
	
	private void updateResult(String input) {
		
		try {
			if (input != null && !input.isEmpty()) {
				String result = HashingModel.getSha256Hash(input);
				resultView.setText(result);
			}
			
			else {
				resultView.setText("");
			}
		}
		
		catch (Exception e) {
			throw new RuntimeException("Error occured in hashing");
		}
	}
}
