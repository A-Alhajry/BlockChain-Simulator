package qu.master.blockchain.documentsattestation.views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
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
	
	private JLabel headerLabel, enterpriseLabel, serviceLabel, newFileLabel, signedFileLabel, commentsLabel;
	private JComboBox<Enterprise> enterprisesList;
	private JComboBox<EnterpriseService> servicesList;
	private JFileChooser fileDialog, signedFileDialog;
	private JTextField fileInput;
	private JTextArea commentsInput;
	private JButton fileButton, signedFileButton, submitButton;
	private boolean isNewFile = true;
	
	Font font, headerFont;
	
	ApplicationController controller = new ApplicationController();
	
	public SignRequestScreen() {
		super();
		//super.setVisible(true);
		
		try {
			headerLabel = new JLabel("New Signature Request");
			enterpriseLabel = new JLabel("Organization:");
			serviceLabel = new JLabel("Service:    ");
			newFileLabel = new JLabel("New Document");
			signedFileLabel = new JLabel("Existing Document");
			commentsLabel = new JLabel("Comments");
			
			EnterpriseComboBoxModel ecbm = new EnterpriseComboBoxModel(controller.getEnterprises());
			ServiceComboBoxModel scbm = new ServiceComboBoxModel(new ArrayList<EnterpriseService>());
			
			enterprisesList = new JComboBox<Enterprise>(ecbm);
			servicesList = new JComboBox<EnterpriseService>(scbm);
					
			fileDialog = new JFileChooser("Select A Document");
			fileDialog.setMultiSelectionEnabled(false);
			fileDialog.setDialogTitle("Select A Document To Sign");
			
			signedFileDialog = new JFileChooser("Select a  Signed Document");
			signedFileDialog.setMultiSelectionEnabled(false);
			signedFileDialog.setDialogTitle("Select a  Signed Document");
			
			fileInput = new JTextField(cols * 50);
			commentsInput = new JTextArea(cols, rows * 5);
			
			fileButton = new JButton("Upload");
			signedFileButton = new JButton("Upload");
			submitButton = new JButton("Submit");
			
			font = new Font(enterpriseLabel.getFont().getName(), enterpriseLabel.getFont().getStyle(), fontSize);
			headerFont = new Font(enterpriseLabel.getFont().getName(), Font.BOLD, fontSize * 2);
			
			headerLabel.setFont(headerFont);
			enterpriseLabel.setFont(font);
			serviceLabel.setFont(font);
			newFileLabel.setFont(font);
			commentsLabel.setFont(font);
			
			initScreen();
			setEvents();
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initScreen() {
		removeAll();
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
		//enterprisePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		super.addControl(enterprisePanel, enterpriseLabel, enterprisesList, insets, labelSize, labelSize);

		JPanel servicesPanel = new JPanel();
		//servicesPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		super.addControl(servicesPanel, serviceLabel, servicesList, insets, labelSize, labelSize);
		
		JPanel commentsPanel = new JPanel();
		//commentsPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		super.addControl(commentsPanel, commentsLabel, commentsInput, insets, labelSize, areaSize);
		
		JPanel filePanel = new JPanel();
		JPanel fileInfoPanel = new JPanel();
		//fileInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		//filePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		super.addControl(fileInfoPanel, newFileLabel, fileInput, insets, new Dimension((int) labelSize.getWidth() / 10, (int) textSize.getHeight()), new Dimension((int) textSize.getWidth(), (int) textSize.getHeight()));
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
		submitButton.setPreferredSize(buttonSize);
		submitButton.setMinimumSize(buttonSize);
		submitPanel.add(submitButton);
		
		//setBorder(BorderFactory.createLineBorder(Color.RED, 2));
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
					isNewFile = true;
				}
			}
		});
		
		enterprisesList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ev) {
				JComboBox<Enterprise> cb = (JComboBox<Enterprise>) ev.getSource();
				Enterprise selectedEnterprise = (Enterprise) cb.getSelectedItem();
				List<EnterpriseService> services;

				if (selectedEnterprise.getId() != null) {
					services = controller.getSignServices(selectedEnterprise.getId());
				}
				
				else {
					services = new ArrayList<>();
				}
				
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
					String allowedFiles = selectedService.getAllowedFilesToString() + ",atts";
					String[] supportedFiles = new String[selectedService.getSupportedFiles().length + 1];
					supportedFiles[0] = "atts";
					for(int i = 1; i < supportedFiles.length; i++) {
						supportedFiles[i] = selectedService.getSupportedFiles()[i - 1];
					}
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Allowed Files : " + allowedFiles, supportedFiles);
					fileDialog.setFileFilter(filter);
				}
			}
		});
		
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SignRequestScreen screen = SignRequestScreen.this;
					
					Enterprise selectedEnterprise = (Enterprise) enterprisesList.getSelectedItem();
					EnterpriseService selectedService = (EnterpriseService) servicesList.getSelectedItem();
					File filePath = fileDialog.getSelectedFile();
					String comments = commentsInput.getText();
					
					if (selectedEnterprise.getId() == null) {
						screen.showDialog("Missing Enterprise", "Please Select an Enterprise", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if (selectedService.getId() == null) {
						screen.showDialog("Missing Service", "Please Select a Service", JOptionPane.ERROR_MESSAGE);
					}
					if (filePath == null) {
						screen.showDialog("File Missing", "Please Select a File", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					String password = JOptionPane.showInputDialog(SignRequestScreen.this, "Please Enter Your Password");
					//String password = "mypassword";

					controller.createSignRequest(password, selectedEnterprise.getId(), selectedService.getId(), filePath.getAbsolutePath(), comments);
					SignRequestScreen.this.showDialog("Success", "Request Successfully Submitted", JOptionPane.INFORMATION_MESSAGE);
					ViewsManager.showClientSignsRequests();
				}
				
				catch (Exception ex) {
					JOptionPane.showMessageDialog(SignRequestScreen.this, "Error Occurred, Please View Logs", "Error Occurred", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
	}
	
}
