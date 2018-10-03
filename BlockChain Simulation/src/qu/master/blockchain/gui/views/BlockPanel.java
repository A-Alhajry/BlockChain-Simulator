package qu.master.blockchain.gui.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import qu.master.blockchain.gui.models.LinkedBlock;
import qu.master.blockchain.gui.models.Block;
import qu.master.blockchain.gui.models.TreeBlock;


public class BlockPanel extends AppPanel {
	private JLabel blockLabel, nonceLabel, dataLabel, hashLabel, resultLabel, prevLabel, rightLabel, leftLabel;
	private JTextField numberText, nonceText, hashText, prevText, rightText, leftText, textText;
	private JTextArea dataArea;
	private JButton mineButton;
	
	private boolean showPrevHashField;
	
	private static final int areaRows = 300;
	private static final int areaCols = 44;
	
	private static final int hGap = 30;
	private static final int vGap = 7;
	
	private static final int labelFontSize = 16;
	
	private static int inputRowsCount = 5;
	
	private Block<String> block;
	
	public BlockPanel(Block<String> block, boolean showPrevHashField) {
		this.block = block;
		this.textText = new JTextField("Test", areaCols);
		this.blockLabel = new JLabel("Block: # ");
		this.nonceLabel = new JLabel("Nonce: ");
		this.dataLabel = new JLabel("Data: ");
		this.hashLabel = new JLabel("Hash: ");
		this.prevLabel = new JLabel("Prev: ");
		this.rightLabel = new JLabel("Right");
		this.leftLabel = new JLabel("Left");
		this.resultLabel = new JLabel("0000000000000000000000000000000000000000000000000000000000000");
		this.hashText = new JTextField(" " + block.getHash(), areaCols);
		this.hashText.setEnabled(true);
		
		this.mineButton = new JButton("Mine");
		this.nonceText = new JTextField(block.getNonce(), areaCols);
		this.dataArea = new JTextArea(areaRows, areaCols);
		
		if (block instanceof LinkedBlock) {
			LinkedBlock<String> actualBlock = (LinkedBlock<String>) block;
			this.numberText = new JTextField("" + block.getSequence(), areaCols);
			this.prevText = new JTextField(" " + actualBlock.getPreviousBlockHash(), areaCols);
			this.prevText.setEnabled(false);
		}
		
		else {
			TreeBlock<String> actualBlock = (TreeBlock<String>) block;
			inputRowsCount = inputRowsCount + 2;
			this.numberText = new JTextField("" + actualBlock.getOrder(), areaCols);
			String rightHash = actualBlock.getRightBlock() == null ? "" : actualBlock.getRightBlockHash();
			String leftHash = actualBlock.getLeftBlock() == null ? "" : actualBlock.getLeftBlockHash();
			
			this.rightText= new JTextField(rightHash, areaCols);
			this.rightText.setEnabled(true);
			
			this.leftText = new JTextField(leftHash, areaCols);
			this.leftText.setEnabled(true);
		}
		
//		this.mineButton = new JButton("Mine") {
//			
//			
//			@Override
//			public void paint(Graphics g) {
//				super.paint(g);
//				setText("Mine");
//				Graphics2D g2d = (Graphics2D) g;
//				Icon icon = this.getIcon();
//				
//				if (icon != null) {
//					Image image = ((ImageIcon) icon).getImage();
//					super.paint(g2d);
//					g2d.drawImage(image, 15, 25, this);
//					g2d.finalize();
//				}
//				
//			}
//		};
		
		//this.dataArea.setLineWrap(true);
		//this.dataArea.setSize(new Dimension(areaCols, areaRows));
		//this.dataArea.setPreferredSize(new Dimension(areaCols, areaRows));
		
		setLabelFont(blockLabel);
		setLabelFont(nonceLabel);
		setLabelFont(dataLabel);
		setLabelFont(hashLabel);
		setLabelFont(prevLabel);
		setLabelFont(rightLabel);
		setLabelFont(leftLabel);
		
		
		this.mineButton.setFont(new Font(blockLabel.getFont().getFontName(), Font.PLAIN, 14));
		this.mineButton.setForeground(Color.BLUE);
		
		this.showPrevHashField = showPrevHashField;
		this.inputRowsCount = showPrevHashField ? inputRowsCount : inputRowsCount + 1;
		
		//setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
		
//		add(blockLabel);
//		add(nonceLabel);
//		add(dataLabel);
//		add(hashLabel);
//		add(resultLabel);
//		add(numberText);
//		add(nonceText);
//		add(dataArea);
//		add(mineButton);
		
	}
	
	@Override
	public void initPanel() {
		//removeAll();
		//setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(layout);
		
		Dimension labelSize = new Dimension(areaCols, areaCols / 3);
		Dimension textSize = new Dimension(areaCols, areaCols / 3);
		Dimension areaSize = new Dimension(areaCols, areaRows);
		Dimension buttonSize = new Dimension(105, 65);
		
		int gridx = 0;
		int gridy = 0;
		int rowNumber = 1;
		
		InputFieldWithLabel numberRow = new InputFieldWithLabel(this.blockLabel, this.numberText, hGap, vGap, labelSize, textSize);
		numberRow.setPreferredSize(new Dimension(this.blockLabel.getWidth() + areaCols + hGap * 2, areaRows / 4));
		addInputRow(gbc, numberRow, gridx, gridy++, rowNumber++, inputRowsCount);
		
		InputFieldWithLabel nonceRow = new InputFieldWithLabel(this.nonceLabel, this.nonceText, hGap, vGap, labelSize, textSize);
		nonceRow.setPreferredSize(new Dimension(this.blockLabel.getWidth() + areaCols + hGap * 2, areaRows / 4));
		gbc = new GridBagConstraints();
		
		addInputRow(gbc, nonceRow, gridx, gridy++, rowNumber++, inputRowsCount);
		
//		InputFieldWithLabel dataRow = new InputFieldWithLabel(this.dataLabel, this.textText, hGap, vGap, labelSize, textSize);
		//dataRow.setPreferredSize(new Dimension(this.blockLabel.getWidth() + areaCols + hGap * 2, areaRows + (int) this.dataArea.getSize().getHeight()));
		InputFieldWithLabel dataRow = new InputFieldWithLabel(this.dataLabel, this.dataArea, hGap, vGap, labelSize, areaSize);
		//dataRow.setPreferredSize(new Dimension(this.blockLabel.getWidth() + areaCols + hGap * 2, areaRows / 4));
		dataRow.setPreferredSize(new Dimension(this.blockLabel.getWidth() + areaCols + hGap * 2, areaRows + (int) this.dataArea.getSize().getHeight() + vGap * 10));


		gbc = new GridBagConstraints();
		addInputRow(gbc, dataRow, gridx, gridy++, rowNumber++, inputRowsCount);
		
		this.dataArea.setMinimumSize(new Dimension(areaCols * 12, areaCols));
		
		if (showPrevHashField) {
			InputFieldWithLabel prevRow = new InputFieldWithLabel(this.prevLabel, this.prevText, hGap, vGap, labelSize, textSize);
			prevRow.setPreferredSize(new Dimension(this.hashLabel.getWidth() + areaCols + hGap * 2, areaRows / 4));
			gbc = new GridBagConstraints();
			addInputRow(gbc, prevRow, gridx, gridy++, rowNumber++, inputRowsCount);
		}
		
		if (this.leftText != null) {
			InputFieldWithLabel rightRow = new InputFieldWithLabel(this.rightLabel, this.rightText, hGap, vGap, labelSize, textSize);
			rightRow.setPreferredSize(new Dimension(this.hashLabel.getWidth() + areaCols + hGap * 2, areaRows / 4));
			gbc = new GridBagConstraints();
			addInputRow(gbc, rightRow, gridx, gridy++, rowNumber++, inputRowsCount);
			
			InputFieldWithLabel leftRow = new InputFieldWithLabel(this.leftLabel, this.leftText, hGap, vGap, labelSize, textSize);
			leftRow.setPreferredSize(new Dimension(this.hashLabel.getWidth() + areaCols + hGap * 2, areaRows / 4));
			gbc = new GridBagConstraints();
			addInputRow(gbc, leftRow, gridx, gridy++, rowNumber++, inputRowsCount);
		}
		
		InputFieldWithLabel hashRow = new InputFieldWithLabel(this.hashLabel, this.hashText, hGap, vGap, labelSize, textSize);
		hashRow.setPreferredSize(new Dimension(this.blockLabel.getWidth() + areaCols + hGap * 2, areaRows / 4));
		gbc = new GridBagConstraints();
		addInputRow(gbc, hashRow, gridx, gridy++, rowNumber++, inputRowsCount);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = rowNumber;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(vGap * 3, hGap, vGap / 3, hGap);
		this.mineButton.setPreferredSize(buttonSize);
		addButtonRow(this.mineButton, gbc);
		//this.mineButton.repaint();
		
		//this.dataArea.setBounds(0, 0, areaCols, areaRows);
		
		//this.dataArea.setText("");
		
	}
	
	private void addInputRow(GridBagConstraints gbc, InputFieldWithLabel component, int gridx, int gridy, int rowNumber, int rowsCount) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(vGap * 3, hGap, vGap / 3, hGap);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.weightx = 1;
		gbc.weighty = rowNumber == rowsCount ? 1 : 0;
		gbc.gridheight = 1;
		add(component, gbc);
		
		
		//component.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
	}
	
	private void addButtonRow(JButton button, GridBagConstraints parentConstraints) {
		JPanel buttonRow = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0;
		gbc.insets = new Insets(vGap, hGap, vGap, hGap);
		
		buttonRow.setLayout(layout);
		buttonRow.setOpaque(false);
		buttonRow.add(button, gbc);
		add(buttonRow, parentConstraints);
		//buttonRow.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
	}
	
	private void setLabelFont(JLabel label) {
		label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, labelFontSize));
	}
	
	public JLabel getBlockLabel() {
		return this.blockLabel;
	}
	
	public JLabel getNonceLabel() {
		return this.nonceLabel;
	}
	
	public JLabel getDataLabel() {
		return this.dataLabel;
	}
	
	public JLabel getHashLabel() {
		return this.hashLabel;
	}
	
	public JLabel getResultLabel() {
		return this.resultLabel;
	}
	
	public JTextField getNumberTextField() {
		return this.numberText;
	}
	
	public JTextField getNonceTextField() {
		return this.nonceText;
	}
	
	public JTextArea getDataTextArea() {
		return this.dataArea;
	}
	
	public JTextField getHashTextField() {
		return this.hashText;
	}
	
	public JTextField getPrevTextField() {
		return this.prevText;
	}
	
	public JTextField getRightTextField() {
		return this.rightText;
	}
	
	public JTextField getLeftTextField() {
		return this.leftText;
	}
	
	public JButton getMineButton() {
		return this.mineButton;
	}
	
	public Block<String> getBlock() {
		return this.block;
	}
	
	@Override
	public String toString() {
		return " Panel " + this.getBlock().getSequence();
	}
}
