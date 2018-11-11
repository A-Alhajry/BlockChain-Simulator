package qu.master.blockchain.documentsattestation.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SignRequestScreen extends JPanel {
	
	private static int hGap = 15;
	private static int vGap = 15;
	
	private static int rows = 15;
	private static int cols = 50;
	
	private static int fontSize = 16;
	
	private JLabel enterpriseLabel, serviceLabel, fileLabel, commentsLabel;
	private JComboBox<String> enterprisesList, servicesList;
	private JFileChooser fileDialog;
	private JTextField fileInput;
	private JTextArea commentsInput;
	private JButton fileButton, signButton;
	
	
	public SignRequestScreen() {
		//super.setVisible(true);
		
		enterpriseLabel = new JLabel("Enterprise:");
		serviceLabel = new JLabel("Service:");
		fileLabel = new JLabel("Document");
		commentsLabel = new JLabel("Comments");
		
		enterprisesList = new JComboBox<String>(new String[] {"Qatar University", "Hamad Medical Corporation", "Ministry Of Interior"});
		servicesList = new JComboBox<String>();
				
		fileDialog = new JFileChooser("Select A Document");
		fileDialog.setMultiSelectionEnabled(false);
		
		fileInput = new JTextField(cols);
		commentsInput = new JTextArea(cols, rows * 5);
		
		fileButton = new JButton("Upload");
		signButton = new JButton("Submit");
		
		Font font = new Font(enterpriseLabel.getFont().getName(), enterpriseLabel.getFont().getStyle(), fontSize);
		
		enterpriseLabel.setFont(font);
		serviceLabel.setFont(font);
		fileLabel.setFont(font);
		commentsLabel.setFont(font);
		
		initPanel();
	}
	
	private void initPanel() {
		
		Dimension textSize = new Dimension(cols * 30, rows);
		String comboBoxProto = new String();
		for(int i = 0; i < cols * 2; i++) {
			comboBoxProto += " ";
		}
		enterprisesList.setPrototypeDisplayValue(comboBoxProto);
		servicesList.setPrototypeDisplayValue(comboBoxProto);
		
		JPanel enterprisePanel = new JPanel();
		enterprisePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		enterprisePanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.WEST;
		gbc.insets = new Insets(hGap, vGap, hGap, vGap);
		enterpriseLabel.setPreferredSize(textSize);
		enterprisePanel.add(enterpriseLabel, gbc);
		
		gbc.gridx = 1;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.VERTICAL;
		enterprisesList.setPreferredSize(textSize);
		enterprisePanel.add(enterprisesList, gbc);
		
		setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		setLayout(new GridBagLayout());
		
		int gridx = 0;
		int gridy = 0;
		addPanel(enterprisePanel, gridx, gridy++);
		
	}
	
	private void addPanel(JPanel panel, int gridx, int gridy) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(hGap, vGap, hGap, vGap);
		add(panel, gbc);
	}
}
