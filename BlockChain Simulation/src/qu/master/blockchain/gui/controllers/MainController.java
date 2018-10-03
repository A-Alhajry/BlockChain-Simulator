package qu.master.blockchain.gui.controllers;

import qu.master.blockchain.gui.views.MainFrame;

public class MainController extends SwingController {
	
	private MainFrame mainFrame;
	
	public MainController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void runController() {
		HashController hashController = new HashController(this.mainFrame.getHashPanel());
		BlockController blockController = new BlockController(this.mainFrame);
		LinearBlockChainController linearBlockChainController = new LinearBlockChainController(this.mainFrame);
		TreeBlockChainController treeBlockChainController = new TreeBlockChainController(this.mainFrame);
		this.mainFrame.setVisible(true);
		hashController.runController();
		blockController.runController();
		linearBlockChainController.runController();
		treeBlockChainController.runController();
		
		this.mainFrame.setScreens();
	}
}
