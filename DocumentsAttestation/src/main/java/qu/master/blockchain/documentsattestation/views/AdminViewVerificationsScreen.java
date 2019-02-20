package qu.master.blockchain.documentsattestation.views;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import qu.master.blockchain.documentsattestation.controllers.ApplicationController;
import qu.master.blockchain.documentsattestation.models.beans.DocumentSignature;
import qu.master.blockchain.documentsattestation.models.beans.SignRequest;
import qu.master.blockchain.documentsattestation.models.beans.VerifyRequest;

public class AdminViewVerificationsScreen extends AbstractScreen {
	
	private JLabel headerLabel;
	
	private static final int fontSize = 16;
	private static final int hGap = 15;
	private static final int vGap = 15;
	
	private ApplicationController appController;
	private List<VerifyRequest> requests;
	
	private AdminVerifyRequestsTable requestsTable;
	
	private JPanel signsTable;

	
	public AdminViewVerificationsScreen() {
		super();
		this.headerLabel = new JLabel("All Submitted Verification Requests");
		appController = new ApplicationController();
		initScreen();
	}
	
	@Override
	public void initScreen() {
		super.removeAll();
		requests = appController.getEnterpriseVerifyRequests("mypassword");
		requestsTable = new AdminVerifyRequestsTable(requests);
		
		requestsTable.setOpenAction((VerifyRequest request) -> onClickOpen(request));
		requestsTable.setSelectAction((VerifyRequest request) -> onClickSelect(request));
		
		Insets insets = new Insets(vGap * 2, hGap, vGap * 3, hGap);
		
		JPanel headerPanel = new JPanel();
		super.addHeader(headerPanel, headerLabel, super.getFont(Font.BOLD, fontSize * 2));
		
		JPanel tablePanel = new JPanel();
		super.addTable(tablePanel, requestsTable, insets, getBoldFont(fontSize), getPlainFont(fontSize));
		
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(vGap, hGap, vGap, hGap);
		
		add(headerPanel, gbc);
		
		gbc.gridy = 1;
		gbc.weighty = 1;
		add(tablePanel, gbc);

		
	}
	
	private void onClickOpen(VerifyRequest request) {
		try {
			String documentId = request.getDocumentId();
			String documentPath = appController.getDocumentPath(documentId, "mypassword");
			Desktop.getDesktop().open(new File(documentPath));
		}
		
		catch (Exception e) {
			super.showDialog("Error Occurred", "Error Occurred, Please View Logs", JOptionPane.ERROR);
		}
	}
	
	private void onClickSelect(VerifyRequest request) {
		try {
			int choice = JOptionPane.showConfirmDialog(null, "Do you want to sign this document ? ");
			
			if (choice == JOptionPane.YES_OPTION) {
				appController.acceptVerifyRequest(request.getDocumentId(), request.getId(), "mypassword");
				super.showDialog("Verification succeeded", "Document was successfully verified !", JOptionPane.INFORMATION_MESSAGE);
			}
			
			else if (choice == JOptionPane.NO_OPTION) {
				appController.rejectVerifyRequest(request.getId());
				super.showDialog("Document Rejected", "Document was successfully rejected ", JOptionPane.INFORMATION_MESSAGE);
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
	
}