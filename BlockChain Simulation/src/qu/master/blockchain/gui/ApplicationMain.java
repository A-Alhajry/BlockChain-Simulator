package qu.master.blockchain.gui;

import qu.master.blockchain.gui.controllers.*;
import javax.swing.*;
import qu.master.blockchain.gui.views.*;


import javax.swing.*;

public class ApplicationMain {
	
	static MainFrame frame;
	
	public static void main(String[] args) {
		
		try {
			//HashingController hashController = new HashingController(frame);
			//hashController.runController();
			//BlockController blockController = new BlockController(frame);
			//blockController.runController();
			
			SwingUtilities.invokeLater(() -> {
				frame = MainFrame.getMainFrameInstance();
				MainController mainController = new MainController(frame);
				mainController.runController();
			});
			
			
		}
		
		catch (Exception e) {
			handleError(frame, e);
		}
	}
	
	private static void handleError(JFrame frame, Exception e) {
		JOptionPane.showMessageDialog(frame, e.getMessage(), "Error occured", JOptionPane.ERROR_MESSAGE);
		e.printStackTrace();
		System.exit(0);
	}
}
