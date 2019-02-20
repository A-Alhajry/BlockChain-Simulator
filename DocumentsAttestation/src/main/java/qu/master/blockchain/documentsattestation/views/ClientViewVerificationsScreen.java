package qu.master.blockchain.documentsattestation.views;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import qu.master.blockchain.documentsattestation.AppUtils;
import qu.master.blockchain.documentsattestation.controllers.ApplicationController;
import qu.master.blockchain.documentsattestation.models.beans.DocumentSignature;
import qu.master.blockchain.documentsattestation.models.beans.SignRequest;
import qu.master.blockchain.documentsattestation.models.beans.VerifyRequest;

public class ClientViewVerificationsScreen extends AbstractScreen {
	
	private JLabel headerLabel;
	
	private int fontSize = 16;
	
	private static int hGap = 15;
	private static int vGap = 15;
	
	private ApplicationController controller;
	private List<VerifyRequest> requests;
	
	private JPanel signsTable;

	
	public ClientViewVerificationsScreen() {
		super();
		headerLabel = new JLabel("View Verifications Requests");
		controller = new ApplicationController();
		initScreen();
	}
	
	@Override
	public void initScreen() {
		removeAll();
		requests = controller.getClientVerifyRequests();
		Insets insets = new Insets(vGap * 2, hGap, vGap * 3, hGap);
		JPanel headerPanel = new JPanel();
		super.addHeader(headerPanel, headerLabel, super.getFont(Font.BOLD, fontSize * 2));
		
		String[] columns = getColumns();
		String[][] rows = getRows(requests);
		
		JPanel tablePanel = new JPanel();
		ClientVerifyRequestsTable table = new ClientVerifyRequestsTable(requests, (VerifyRequest req) -> onClickSave(req), ((VerifyRequest req) -> onClickSigns(req)));
		addTable(tablePanel, table, insets, super.getFont(Font.BOLD, fontSize), super.getFont(Font.PLAIN, fontSize));
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(hGap, vGap, hGap, vGap);
		
		add(headerPanel, gbc);
		
		gbc.gridy = 1;
		gbc.weighty = 1;
		add(tablePanel, gbc);
		

	}
	
	private void onClickSave(VerifyRequest req) {
		try {
			JButton saveButton = new JButton("Save");
			JFileChooser fileDialog = new JFileChooser();
			fileDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileDialog.setAcceptAllFileFilterUsed(false);
			int result = fileDialog.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFolder = fileDialog.getSelectedFile();
				controller.saveAndGetAttestationFile(req.getDocument(), selectedFolder.getAbsolutePath());
				Desktop.getDesktop().open(selectedFolder);
			}
		}
		
		catch (Exception e) {
			super.showDialog("Error Occurred", "Error Occurred, Please View Logs", JOptionPane.ERROR);
		}
		
	}
	
	private void onClickSigns(VerifyRequest req) {
		List<DocumentSignature> signs = req.getDocument().getSignHistory();
		if (signsTable != null) {
			remove(signsTable);
		}
		
		SignaturesTable table = new SignaturesTable(signs);
		signsTable = new JPanel();
		signsTable.setVisible(true);
		addTable(signsTable, table, new Insets(vGap * 2, hGap, vGap * 3, hGap), super.getFont(Font.BOLD, fontSize), super.getFont(Font.PLAIN, fontSize));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 2;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(hGap, vGap, hGap, vGap);
		add(signsTable, gbc);
		revalidate();

		
		
		
	}
	
	private String[] getColumns() {
		return new String[] {"Enterprise", "Service", "Date", "Status"};
	}
	
	private String[][] getRows(List<VerifyRequest> requests) {
		String[][] result = new String[requests.size() + 1][4];
		
		for(int i = 0; i < requests.size(); i++) {
			VerifyRequest request = requests.get(i);
			String[] row = new String[] 
					{request.getEnterprise().getName(), request.getService().getTitle(), 
							AppUtils.formatDate(request.getRequestTime(), "yyyy-MM-dd HH:mm"), request.getStatus().getName()};
			result[i] = row;
		}
		
		//result[requests.size()] = new String[] {"   ", "    ", "   ", "   "};
 		
		return result;
	}
}
