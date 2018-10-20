package qu.master.blockchain.gui.controllers;

import java.util.*;

import qu.master.blockchain.gui.views.MainFrame;

public class MainController extends SwingController {
	
	private MainFrame mainFrame;
	private List<SwingController> innerControllers;
	
	public MainController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.innerControllers = new ArrayList<>();
	}
	
	@Override
	public void runController() {
		innerControllers.add(new HashController(this.mainFrame.getHashPanel()));
		innerControllers.add(new BlockController(this.mainFrame));
		innerControllers.add(new LinearBlockChainController(this.mainFrame));
		innerControllers.add(new TreeBlockChainController(this.mainFrame));
		innerControllers.add(new KeysController(this.mainFrame));
		innerControllers.add(new TransactionController(this.mainFrame));
		
		this.mainFrame.pack();
		this.mainFrame.setVisible(true);
		
		for(SwingController controller : this.innerControllers) {
			controller.runController();
		}
		
		this.mainFrame.setScreens();
	}
}
