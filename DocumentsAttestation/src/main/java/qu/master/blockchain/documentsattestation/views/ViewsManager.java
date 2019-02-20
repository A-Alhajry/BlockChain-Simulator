package qu.master.blockchain.documentsattestation.views;

import javax.swing.SwingUtilities;

public class ViewsManager{
	
	private static ClientMainScreen clientScreen;
	private static AdminMainScreen adminScreen;
	
	private static final int width = 1300;
	private static final int height = 1000;
	
	public static void showClientScreen() {
		SwingUtilities.invokeLater(() -> clientScreen = new ClientMainScreen("Documents Attestation", width, height));
	}
	
	public static void showAdminScreen(String enterpriseName) {
		SwingUtilities.invokeLater(() -> adminScreen = new AdminMainScreen(enterpriseName + " Documents Attestation Dashboard", width, height));
	}
	
	public static void showClientSignRequestForm() {
		clientScreen.showSignRequestForm();
	}
	
	public static void showClientVerifyRequestForm() {
		clientScreen.showVerifyRequestForm();
	}
	
	public static void showClientSignsRequests() {
		clientScreen.showClientSignRequestsScreen();
	}
	
	public static void showClientVerifyRequests() {
		clientScreen.showClientVerifyRequestsScreen();
	}
}
