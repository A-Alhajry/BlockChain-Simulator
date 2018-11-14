package qu.master.blockchain.documentsattestation.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import qu.master.blockchain.documentsattestation.controllers.ApplicationController;
import qu.master.blockchain.documentsattestation.models.beans.Enterprise;
import qu.master.blockchain.documentsattestation.models.beans.EnterpriseService;

public class SignRequestScreen extends AbstractScreen {
	
	private static int hGap = 15;
	private static int vGap = 15;
	
	private static int rows = 15;
	private static int cols = 50;
	
	private static int fontSize = 16;
	
	private JLabel headerLabel, enterpriseLabel, serviceLabel, fileLabel, commentsLabel;
	private JComboBox<Enterprise> enterprisesList;
	private JComboBox<EnterpriseService> servicesList;
	private JFileChooser fileDialog;
	private JTextField fileInput;
	private JTextArea commentsInput;
	private JButton fileButton, signButton;
	
	
	Font font, headerFont;
	
	ApplicationController controller = new ApplicationController();
	
	public SignRequestScreen() {
		super();
		//super.setVisible(true);
		
		try {
			headerLabel = new JLabel("New Request");
			enterpriseLabel = new JLabel("Enterprise:");
			serviceLabel = new JLabel("Service:    ");
			fileLabel = new JLabel("Document");
			commentsLabel = new JLabel("Comments");
			
			EnterpriseComboBoxModel ecbm = new EnterpriseComboBoxModel(controller.getEnterprises());
			ServiceComboBoxModel scbm = new ServiceComboBoxModel(new ArrayList<EnterpriseService>());
			
			enterprisesList = new JComboBox<Enterprise>(ecbm);
			servicesList = new JComboBox<EnterpriseService>(scbm);
					
			fileDialog = new JFileChooser("Select A Document");
			fileDialog.setMultiSelectionEnabled(false);
			fileDialog.setDialogTitle("Select A Document To Sign");
			
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
			setEvents();
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initPanel() {
		
		Dimension labelSize = new Dimension(cols * 30, rows * 2);
		Dimension textSize = new Dimension(cols * 30, rows * 3);
		Dimension areaSize = new Dimension(cols * 30, rows * 20);
		Dimension buttonSize = new Dimension(85, 65);
		
		Insets insets = new Insets(hGap, vGap, hGap, vGap);
		String comboBoxProto = new String();
		for(int i = 0; i < cols * 2; i++) {
			comboBoxProto += " ";
		}
		
		Enterprise proto = new Enterprise();
		EnterpriseService protoService = new EnterpriseService();
		proto.setName(comboBoxProto);
		enterprisesList.setPrototypeDisplayValue(proto);
		servicesList.setPrototypeDisplayValue(protoService);
		
		//enterprisesList.setRenderer(new DefaultComboBoxRenderer("Select An Enterprise"));
		//servicesList.setRenderer(new DefaultComboBoxRenderer("Select A Service"));
		
		//enterprisesList.setSelectedIndex(-1);

		JPanel headerPanel = new JPanel();
		super.addHeader(headerPanel, headerLabel, headerFont);
		JPanel enterprisePanel = new JPanel();
		enterprisePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		super.addControl(enterprisePanel, enterpriseLabel, enterprisesList, insets, labelSize, labelSize);

		JPanel servicesPanel = new JPanel();
		servicesPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		super.addControl(servicesPanel, serviceLabel, servicesList, insets, labelSize, labelSize);
		
		JPanel commentsPanel = new JPanel();
		commentsPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		super.addControl(commentsPanel, commentsLabel, commentsInput, insets, labelSize, areaSize);
		
		JPanel filePanel = new JPanel();
		JPanel fileInfoPanel = new JPanel();
		fileInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		filePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		super.addControl(fileInfoPanel, fileLabel, fileInput, insets, new Dimension((int) labelSize.getWidth() / 10, (int) labelSize.getHeight()), new Dimension((int) textSize.getWidth() / 2, (int) textSize.getHeight()));
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
		super.addRow(this, headerPanel, insets, gridx, gridy++);
		super.addRow(this, enterprisePanel, insets, gridx, gridy++);
		super.addRow(this, servicesPanel, insets, gridx, gridy++);
		super.addRow(this, commentsPanel, insets, gridx, gridy++);
		super.addRow(this, filePanel, insets, gridx, gridy++);
		super.addRow(this, submitPanel, insets, gridx, gridy++);

	}
	
	private void setEvents() throws Exception {
		fileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				int fileDialogReturn = fileDialog.showOpenDialog(SignRequestScreen.this);
				if (fileDialogReturn == JFileChooser.APPROVE_OPTION) {
					fileInput.setText(fileDialog.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		enterprisesList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ev) {
				JComboBox<Enterprise> cb = (JComboBox<Enterprise>) ev.getSource();
				Enterprise selectedEnterprise = (Enterprise) cb.getSelectedItem();
				List<EnterpriseService> services = controller.getSignServices(selectedEnterprise.getId());
				servicesList.removeAllItems();
				for(EnterpriseService service : services) {
					servicesList.addItem(service);
				}
			}
		});
		
		servicesList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				JComboBox<EnterpriseService> cb = (JComboBox<EnterpriseService>) ev.getSource();
				EnterpriseService selectedService = (EnterpriseService) cb.getSelectedItem();
				if (selectedService != null) {
					String allowedFiles = selectedService.getAllowedFilesToString();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Allowed Files : " + allowedFiles, selectedService.getSupportedFiles());
					fileDialog.setFileFilter(filter);
				}
			}
		});
	}
	
}
