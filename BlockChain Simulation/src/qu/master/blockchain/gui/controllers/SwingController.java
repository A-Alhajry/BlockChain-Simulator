package qu.master.blockchain.gui.controllers;

import javax.swing.*;

public abstract class SwingController {
	
	protected void runFrame(JFrame frame) {
		frame.setVisible(true);
	}
	
	public abstract void runController();
}
