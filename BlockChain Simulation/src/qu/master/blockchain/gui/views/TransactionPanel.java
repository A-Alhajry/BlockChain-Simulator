package qu.master.blockchain.gui.views;

import javax.swing.*;
import java.awt.*;

public class TransactionPanel extends AppPanel {
	
	private JLabel prevTxHashLabel, outputIndexLabel, valueLabel, publicKeyLabel, signatureLabel;
	private JTextField prevTxHashField, outputIndexField, valueField, publicKeyField, signatureField;
	private JButton signButton, verifyButton;
	
	private static int rows = 15;
	private static int cols = 60;
	private static int fontSize = 16;
	private static int hGap = 12;
	private static int vGap = 12;
	
	private static int totalRows = 5;
	
	
	public TransactionPanel() {
		this.prevTxHashLabel = new JLabel("Previous Tx Hash");
		this.outputIndexLabel = new JLabel("Output Index");
		this.valueLabel = new JLabel("Value               ");
		this.publicKeyLabel = new JLabel("Public Key");
		this.signatureLabel = new JLabel("Signature");
		
		this.prevTxHashField = new JTextField(cols);
		this.outputIndexField = new JTextField(cols);
		this.valueField = new JTextField(cols);
		this.publicKeyField = new JTextField(cols);
		this.signatureField = new JTextField(cols);
		this.signatureField.setEnabled(false);
		
		this.signButton = new JButton("Sign");
		this.verifyButton = new JButton("Verify");
	}
	
	
	
	@Override
	public void initPanel() {
		setLayout(new GridBagLayout());
		
		Font labelFont = new Font(prevTxHashLabel.getFont().getFontName(), prevTxHashLabel.getFont().getStyle(), fontSize);
		prevTxHashLabel.setFont(labelFont);
		outputIndexLabel.setFont(labelFont);
		valueLabel.setFont(labelFont);
		publicKeyLabel.setFont(labelFont);
		signatureLabel.setFont(labelFont);
		
		int gridy = 0;
		addInputRow(prevTxHashLabel, prevTxHashField, gridy++, gridy, totalRows);
		addInputRow(outputIndexLabel, outputIndexField, gridy++, gridy, totalRows);
		addInputRow(valueLabel, valueField, gridy++, gridy, totalRows);
		addInputRow(publicKeyLabel, publicKeyField, gridy++, gridy, totalRows);
		addInputRow(signatureLabel, signatureField, gridy++, gridy, totalRows);
		addButtonsRow(gridy++);
	}
	
	private void addInputRow(JLabel label, JTextField field, int gridy, int rowNumber, int totalRows) {
		Dimension labelSize = new Dimension(cols * 30, rows);
		Dimension textSize = new Dimension(cols * 30, rows);
		
		field.setMinimumSize(textSize);
		field.setPreferredSize(textSize);
		
		InputFieldWithLabel rowPanel = new InputFieldWithLabel(label, field, hGap, vGap, labelSize, textSize);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = gridy;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = new Insets(vGap * 2, hGap * 2, vGap * 2, hGap * 2);
		gbc.ipady = (int) textSize.getHeight() + vGap * 4;
		
		//rowPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		
		add(rowPanel, gbc);
	}
	
	private void addButtonsRow(int gridy) {
		JPanel buttonsRow = new JPanel();
		
		Dimension buttonSize = new Dimension(105, 65);
		
		signButton.setPreferredSize(buttonSize);
		verifyButton.setPreferredSize(buttonSize);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = gridy;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(vGap, hGap, vGap, hGap);
		
		buttonsRow.setLayout(new GridBagLayout());
		buttonsRow.add(signButton, gbc);
		
		gbc.gridx = 1;
		buttonsRow.add(verifyButton, gbc);
		
		gbc.gridx = 0;
		gbc.insets = new Insets(vGap / 2, hGap * 2, vGap / 2, hGap * 2);
		add(buttonsRow, gbc);
	}
	
	public JTextField getPrevTxHashField() {
		return this.prevTxHashField;
	}
	
	public JTextField getOutputIndexField() {
		return this.outputIndexField;
	}
	
	public JTextField getValueField() {
		return this.valueField;
	}
	
	public JTextField getPublicKeyField() {
		return this.publicKeyField;
	}
	
	public JTextField getSignatureField() {
		return this.signatureField;
	}
	
	public JButton getSignButton() {
		return this.signButton;
	}
	
	public JButton getVerifyButton() {
		return this.verifyButton;
	}
}
