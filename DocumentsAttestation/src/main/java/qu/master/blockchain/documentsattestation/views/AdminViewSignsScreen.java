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

public class AdminViewSignsScreen extends AbstractScreen {
	
	private JLabel headerLabel;
	
	private static final int fontSize = 16;
	private static final int hGap = 15;
	private static final int vGap = 15;
	
	private ApplicationController appController;
	private List<SignRequest> requests;
	
	private AdminSignRequestsTable requestsTable;
	private JPanel signsTable;

	
	public AdminViewSignsScreen() {
		super();
		this.headerLabel = new JLabel("All Submitted Signature Requests");
		appController = new ApplicationController();
		initScreen();
	}
	
	@Override
	public void initScreen() {
		super.removeAll();
		requests = appController.getEnterpriseSignRequests("mypassword");
		requestsTable = new AdminSignRequestsTable(requests);
		
		requestsTable.setOpenAction((SignRequest request) -> onClickOpen(request));
		requestsTable.setSelectAction((SignRequest request) -> onClickSelect(request));
		requestsTable.setSignsAction((SignRequest request) -> onClickSigns(request));
		
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
	
	private void onClickOpen(SignRequest request) {
		try {
			String documentId = request.getDocumentId();
			String documentPath = appController.getDocumentPath(documentId, "mypassword");
			Desktop.getDesktop().open(new File(documentPath));
		}
		
		catch (Exception e) {
			super.showDialog("Error Occurred", "Error Occurred, Please View Logs", JOptionPane.ERROR);
		}
	}
	
	private void onClickSigns(SignRequest req) {
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
	
	private void onClickSelect(SignRequest request) {
		try {
			int choice = JOptionPane.showConfirmDialog(null, "Do you want to verify this document ? ");
			
			if (choice == JOptionPane.YES_OPTION) {
				appController.acceptSignRequest(request.getDocumentId(), request.getId(), "mypassword");
				super.showDialog("Signing succeeded", "Document was successfully signed !", JOptionPane.INFORMATION_MESSAGE);
			}
			
			else if (choice == JOptionPane.NO_OPTION) {
				appController.rejectSignRequest(request.getId());
				super.showDialog("Document Rejected", "Document was successfully verified ", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		catch (Exception e) {
			super.showDialog("Error Occurred", "Error Occurred, Please View Logs", JOptionPane.ERROR);
		}
	}
	
}