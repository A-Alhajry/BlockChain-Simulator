package qu.master.blockchain.gui.views;

import javax.swing.*;

public class SwingUtils {
	
	public static String showInputDialog(String message) {
		return JOptionPane.showInputDialog(MainFrame.getMainFrameInstance(), message);
	}
	
	public static void displayErrorPopup(String title, String message) {
		displayPopup(title, message, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void displayInfoPopup(String title, String message) {
		displayPopup(title, message, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void displayOkPopup(JFrame frame, String title, String message) {
		//displayPopup(frame, title, message, JOptionPane.S)
	}
	
	public static void displayWarningPopup(String title, String message) {
		displayPopup(title, message, JOptionPane.WARNING_MESSAGE);
	}
	public static void displayPopup(String title, String message, int messageType) {
		JOptionPane.showMessageDialog(MainFrame.getMainFrameInstance(), message, title, messageType);
	}
}