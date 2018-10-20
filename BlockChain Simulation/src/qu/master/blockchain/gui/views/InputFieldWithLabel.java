package qu.master.blockchain.gui.views;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.NoSuchElementException;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent;

public class InputFieldWithLabel extends JLabel {
	
	private JLabel label;
	private JComponent inputField;
	private ButtonGroup buttonGroup;
	
	public InputFieldWithLabel(JLabel label, JTextComponent inputField, int hGap, int vGap, Dimension labelSize, Dimension inputFieldSize) {
		super();
		
		this.label = label;
		this.inputField = inputField;
//		BorderLayout layout = new BorderLayout();
//		layout.setHgap(hGap);
//		layout.setVgap(vGap);
//		setLayout(new FlowLayout());
//		add(label, BorderLayout.WEST);
//		add(inputField, BorderLayout.CENTER);
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(layout);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		//gbc.weightx = 0;
		gbc.fill = GridBagConstraints.WEST;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.ipady = (int) labelSize.getHeight();
		gbc.insets = new Insets(vGap, hGap, vGap, hGap);
		add(label, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		//gbc.weightx = 1;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.ipady = (int) inputFieldSize.getHeight();
		gbc.insets = new Insets(vGap, hGap, vGap, hGap);
		add(inputField, gbc);
		
		//label.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		//inputField.setBorder(BorderFactory.createLineBorder(Color.CYAN));
	}
	
	public InputFieldWithLabel(JLabel label, ButtonGroup buttonGroup, int hGap, int vGap, Dimension labelSize, Dimension buttonGroupSize) {
		this.label = label;
		this.buttonGroup = buttonGroup;
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(layout);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.WEST;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = new Insets(vGap, hGap, hGap, vGap);
		gbc.ipady = (int) label.getSize().getHeight();
		add(label, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = new Insets(hGap, hGap, vGap, hGap);
		int ipady = 0;
		
		try {
			AbstractButton button = buttonGroup.getElements().nextElement();
			ipady = ipady + (int) button.getSize().getHeight();
			add(button, gbc);

		}
		
		catch(NoSuchElementException e) {
			gbc.ipady = ipady;
		}
		

		
		
	}
	
	public JLabel getLabel() {
		return this.label;
	}
	
	public JComponent getInputField() {
		return this.inputField;
	}
}
