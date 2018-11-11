package qu.master.blockchain.gui.views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class KeysPanel extends AppPanel {
	
	private JLabel publicKeyLabel, privateKeyLabel;
	private JTextField publicKeyField;
	private JPasswordField privateKeyField;
	private JButton generateButton, saveButton, loadButton;
	
	private static final int rows = 10;
	private static final int cols = 30;
	private static final int fontSize = 16;
	private static final int hGap = 12;
	private static final int vGap = 12;
	
	public KeysPanel() {
		publicKeyLabel = new JLabel("Public Key");
		privateKeyLabel = new JLabel("Private Key");
		publicKeyField = new JTextField(cols);
		privateKeyField = new JPasswordField(cols);
		generateButton = new JButton("Generate");
		saveButton = new JButton("Save");
		loadButton = new JButton("Load");
	}
	
	@Override
	public void initPanel() {
		
	    //setBorder(BorderFactory.createLineBorder(Color.RED, 3));
	    
	    publicKeyLabel.setFont(new Font(publicKeyLabel.getFont().getName(), publicKeyLabel.getFont().getStyle(), fontSize));
	    privateKeyLabel.setFont(publicKeyLabel.getFont());
	    
		Dimension labelSize = new Dimension(cols * 30, rows);
		Dimension textSize = new Dimension(cols * 30, rows);
		Dimension buttonSize = new Dimension(105, 65);
		
		publicKeyField.setMinimumSize(textSize);
		publicKeyField.setPreferredSize(textSize);
		privateKeyField.setMinimumSize(textSize);
		privateKeyField.setPreferredSize(textSize);
		
		InputFieldWithLabel publicKeyPanel = new InputFieldWithLabel(publicKeyLabel, publicKeyField, hGap / 2, vGap, labelSize, textSize);
		InputFieldWithLabel privateKeyPanel = new InputFieldWithLabel(privateKeyLabel, privateKeyField, hGap / 2, vGap, labelSize, textSize);
		
		
		//publicKeyField.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
		publicKeyPanel.setPreferredSize(new Dimension((int) publicKeyLabel.getWidth() + cols + hGap * 2, rows / 4));
		privateKeyPanel.setPreferredSize(new Dimension((int) privateKeyLabel.getWidth() + cols + hGap * 2, rows / 4));
		
		//publicKeyPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		//privateKeyPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = new Insets(vGap * 2, hGap * 2, vGap * 2, hGap * 2);
		gbc.ipadx = hGap * 80;
		gbc.ipady = vGap * 4 + (int) publicKeyLabel.getSize().getHeight();
		add(publicKeyPanel, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = new Insets(vGap * 2, hGap * 2, vGap * 2, hGap * 2);
		gbc.ipady = vGap * 4 + (int) privateKeyLabel.getSize().getHeight();
		//gbc.gridheight = 1;
		add(privateKeyPanel, gbc);
		
		JPanel buttonPanel = new JPanel();
		generateButton.setPreferredSize(buttonSize);
		saveButton.setPreferredSize(buttonSize);
		loadButton.setPreferredSize(buttonSize);
		
		buttonPanel.add(generateButton);
		
		gbc.gridx = 1;
		buttonPanel.add(saveButton);
		
		gbc.gridx = 2;
		buttonPanel.add(loadButton);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = new Insets(vGap, hGap, vGap, hGap);
		add(buttonPanel, gbc);
		
		
		
		
	}
	
	public JButton getGenerateButton() {
		return this.generateButton;
	}
	
	public JButton getSaveButton() {
		return this.saveButton;
	}
	
	public JButton getLoadButton() {
		return this.loadButton;
	}
	
	public JTextField getPublicKeyField() {
		return this.publicKeyField;
	}
	
	public JTextField getPrivateKeyField() {
		return this.privateKeyField;
	}
}