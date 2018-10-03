package qu.master.blockchain.gui.views;

import javax.swing.*;
import java.awt.*;


public class HashPanel extends  AppPanel {
	
	private JLabel dataLabel, hashLabel, resultLabel;
	private JTextArea inputField;
	private JTextField resultField;
	
	private static final int hGap = 30;
	private static final int vGap = 7;
	
	private static final int areaRows = 500;
	private static final int areaCols = 40;
	
	private static final int labelFontSize = 16;
	
	public HashPanel() {
		dataLabel = new JLabel("Data:");
		dataLabel.setFont(new Font(dataLabel.getFont().getFontName(), Font.BOLD, labelFontSize));
		hashLabel = new JLabel("Hash:");
		inputField = new JTextArea(areaRows, areaCols);
		inputField.setEditable(true);
		hashLabel = new JLabel("Hash:");
		hashLabel.setFont(new Font(hashLabel.getFont().getFontName(), Font.BOLD, labelFontSize));
		resultField = new JTextField();
		resultField.setText("0000000000000000000000000000000000000000000000000000000000000");
		resultField.setEditable(false);
		resultLabel = new JLabel("0000000000000000000000000000000000000000000000000000000000000");
		resultLabel.setFont(new Font(resultLabel.getFont().getFontName(), Font.ITALIC, labelFontSize - 2));
		//resultLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		initPanel();
	}
	
	@Override
	public void initPanel() {
		//removeAll();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		
		JPanel inputGroupPanel = new JPanel();
		BorderLayout igLayout = new BorderLayout();
		igLayout.setHgap(hGap);
		igLayout.setVgap(vGap);
		inputGroupPanel.setLayout(igLayout);
		
		ControlWrapper<JLabel> dataLabelControl = new ControlWrapper<>(dataLabel);
		ControlWrapper<JTextArea> dataAreaControl = new ControlWrapper<>(inputField);
		
		inputGroupPanel.add(dataLabelControl, BorderLayout.WEST);
		inputGroupPanel.add(dataAreaControl, BorderLayout.CENTER);
		//inputGroupPanel.setBackground(Color.RED);
		
		//inputGroupPanel.setPreferredSize(new Dimension(100, 100));
		//inputGroupPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		setLayout(layout);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 0.5;
		constraints.gridheight = 1;
		constraints.ipady = areaRows;
		constraints.insets = new Insets(vGap * 4, hGap, 0, hGap);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		
		//inputGroupPanel.setPreferredSize(new Dimension((int) dataLabelControl.getWidth() + dataAreaControl.getWidth(), areaRows * 2 + vGap * 10 + dataAreaControl.getHeight()));
		inputGroupPanel.setPreferredSize(new Dimension(400, 400));
		add(inputGroupPanel, constraints);
		
		JPanel resultGroupPanel = new JPanel();
		BorderLayout rgLayout = new BorderLayout();
		resultGroupPanel.setLayout(rgLayout);
		rgLayout.setHgap(hGap);
		rgLayout.setVgap(vGap);
		
		ControlWrapper<JLabel> hashLabelControl = new ControlWrapper<JLabel>(hashLabel);
		ControlWrapper<JTextField> resultFieldControl = new ControlWrapper<JTextField>(resultField);
		ControlWrapper<JLabel> resultLabelControl = new ControlWrapper<JLabel>(resultLabel);
		
		resultGroupPanel.add(hashLabelControl, BorderLayout.WEST);
		//resultGroupPanel.add(resultFieldControl, BorderLayout.CENTER);
		resultGroupPanel.add(resultLabelControl, BorderLayout.CENTER);
		
		//resultGroupPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 1));
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, hGap, vGap * 4, hGap);
		constraints.anchor = GridBagConstraints.LAST_LINE_START;
		add(resultGroupPanel, constraints);
		
		//dataLabelControl.setPreferredSize(new Dimension(40, 50));
		//dataLabelControl.setSize(dataLabelControl.getPreferredSize());
		//dataLabelControl.setMinimumSize(new Dimension(40, 50));
		//dataLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		//dataLabel.setBounds(0, 0, 120, 120);
		//dataLabelControl.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
		
//		GridBagLayout inputLayout = new GridBagLayout();
//		GridBagConstraints inputConstraints = new GridBagConstraints();
//		
//		inputGroupPanel.setLayout(inputLayout);
//		
//		inputConstraints.gridx = 0;
//		inputConstraints.gridy = 0;
//		inputConstraints.fill = GridBagConstraints.HORIZONTAL;
//		inputConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
//		
//		inputGroupPanel.add(dataLabelControl, inputConstraints);
//		
//		inputConstraints = new GridBagConstraints();
//		inputConstraints.gridx = 1;
//		inputConstraints.gridy = 0;
//		inputConstraints.fill = GridBagConstraints.BOTH;
//		
//		inputGroupPanel.add(dataAreaControl, inputConstraints);
		
		
		
		
//		setLayout(new BorderLayout());
//		
//		JPanel inputGroupPanel = new JPanel();
//		
//		BorderLayout borderLayout = new BorderLayout();
//		borderLayout.setHgap(hGap);
//		
//		JPanel dataPanel = new JPanel();
//		dataPanel.setPreferredSize(new Dimension(30, 40));
//		
//		//setLayout(layout);
//		inputGroupPanel.setLayout(borderLayout);
//		
//		constraints.fill = GridBagConstraints.BASELINE;
//		constraints.gridx = 0;
//		constraints.gridy = 0;
//		//add(dataLabel, constraints);
//		dataPanel.add(dataLabel);
//		inputGroupPanel.add(dataPanel, BorderLayout.WEST);
//		
//		constraints.fill = GridBagConstraints.BASELINE;
//		constraints.gridx = 3;
//		constraints.gridy = 0;
//		//add(inputField, constraints);
//		inputGroupPanel.add(inputField, BorderLayout.CENTER);
//		
//		inputGroupPanel.setPreferredSize(new Dimension(50, 50));
//		inputGroupPanel.setMaximumSize(new Dimension(50, 50));
//		inputGroupPanel.setMinimumSize(new Dimension(50, 50));
//		
//		//setBackground(Color.RED);
//		dataLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
//		inputField.setBorder(BorderFactory.createLineBorder(Color.RED));
//		inputGroupPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
//		
//		add(inputGroupPanel, BorderLayout.NORTH);
		
		
	}
	
	public JTextArea getInputField() {
		return this.inputField;
	}
	
	public JLabel getResultField() {
		return this.resultLabel;
	}
}
