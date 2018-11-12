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

public class SignRequestScreen extends AbstractScreen {
	
	private static int hGap = 15;
	private static int vGap = 15;
	
	private static int rows = 15;
	private static int cols = 50;
	
	private static int fontSize = 16;
	
	private JLabel headerLabel, enterpriseLabel, serviceLabel, fileLabel, commentsLabel;
	private JComboBox<String> enterprisesList, servicesList;
	private JFileChooser fileDialog;
	private JTextField fileInput;
	private JTextArea commentsInput;
	private JButton fileButton, signButton;
	
	Font font, headerFont;
	
	
	public SignRequestScreen() {
		super();
		//super.setVisible(true);
		
		headerLabel = new JLabel("New Request");
		enterpriseLabel = new JLabel("Enterprise:");
		serviceLabel = new JLabel("Service:    ");
		fileLabel = new JLabel("Document");
		commentsLabel = new JLabel("Comments");
		
		enterprisesList = new JComboBox<String>(new String[] {"Qatar University", "Hamad Medical Corporation", "Ministry Of Interior"});
		servicesList = new JComboBox<String>(new String[] {"Qatar University", "Hamad Medical Corporation", "Ministry Of Interior"});
				
		fileDialog = new JFileChooser("Select A Document");
		fileDialog.setMultiSelectionEnabled(false);
		
		fileInput = new JTextField(cols);
		commentsInput = new JTextArea(cols, rows * 5);
		
		fileButton = new JButton("Upload");
		signButton = new JButton("Submit");
		
		font = new Font(enterpriseLabel.getFont().getName(), enterpriseLabel.getFont().getStyle(), fontSize);
		headerFont = new Font(enterpriseLabel.getFont().getName(), Font.BOLD, fontSize * 2);
		
		headerLabel.setFont(headerFont);
		enterpriseLabel.setFont(font);
		serviceLabel.setFont(font);
		fileLabel.setFont(font);
		commentsLabel.setFont(font);
		
		initPanel();
	}
	
	private void initPanel() {
		
		Dimension textSize = new Dimension(cols * 30, rows * 2);
		Dimension areaSize = new Dimension(cols * 30, rows * 20);
		Dimension buttonSize = new Dimension(85, 65);
		
		Insets insets = new Insets(hGap, vGap, hGap, vGap);
		String comboBoxProto = new String();
		for(int i = 0; i < cols * 2; i++) {
			comboBoxProto += " ";
		}
		enterprisesList.setPrototypeDisplayValue(comboBoxProto);
		servicesList.setPrototypeDisplayValue(comboBoxProto);
		
		//enterprisesList.setRenderer(new DefaultComboBoxRenderer("Select An Enterprise"));
		//servicesList.setRenderer(new DefaultComboBoxRenderer("Select A Service"));
		
		//enterprisesList.setSelectedIndex(-1);

		JPanel headerPanel = new JPanel();
		super.addHeader(headerPanel, headerLabel, headerFont);
		JPanel enterprisePanel = new JPanel();
		enterprisePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		super.addControl(enterprisePanel, enterpriseLabel, enterprisesList, insets, textSize, textSize);

		JPanel servicesPanel = new JPanel();
		servicesPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		super.addControl(servicesPanel, serviceLabel, servicesList, insets, textSize, textSize);
		
		JPanel commentsPanel = new JPanel();
		commentsPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		super.addControl(commentsPanel, commentsLabel, commentsInput, insets, textSize, areaSize);
		
		JPanel filePanel = new JPanel();
		JPanel fileInfoPanel = new JPanel();
		fileInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		filePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		super.addControl(fileInfoPanel, fileLabel, fileInput, insets, new Dimension((int) textSize.getWidth() / 10, (int) textSize.getHeight()), new Dimension((int) textSize.getWidth() / 2, (int) textSize.getHeight()));
		fileButton.setPreferredSize(buttonSize);
		fileButton.setMinimumSize(buttonSize);
		filePanel.setLayout(new GridBagLayout());
		GridBagConstraints fileGbc = new GridBagConstraints();
		fileGbc.gridx = 0;
		fileGbc.gridy = 0;
		fileGbc.weightx = 1;
		fileGbc.weighty = 1;
		fileGbc.fill = GridBagConstraints.HORIZONTAL;
		fileGbc.anchor = GridBagConstraints.FIRST_LINE_START;
		fileGbc.insets = insets;
		filePanel.add(fileInfoPanel , fileGbc);
		fileGbc.gridx = 1;
		fileGbc.weightx = 0;
		filePanel.add(fileButton, fileGbc);
		
		JPanel submitPanel = new JPanel();
		submitPanel.setLayout(new GridBagLayout());
		signButton.setPreferredSize(buttonSize);
		signButton.setMinimumSize(buttonSize);
		submitPanel.add(signButton);
		
		setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		setLayout(new GridBagLayout());
		
		int gridx = 0;
		int gridy = 0;
		addPanel(headerPanel, gridx, gridy++);
		addPanel(enterprisePanel, gridx, gridy++);
		addPanel(servicesPanel, gridx, gridy++);
		addPanel(commentsPanel, gridx, gridy++);
		addPanel(filePanel, gridx, gridy++);
		addPanel(submitPanel, gridx, gridy++);
		
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
