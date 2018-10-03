package qu.master.blockchain.gui.views;


import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import qu.master.blockchain.gui.models.LinkedBlock;

public class MainFrame extends AppFrame{

	
	//private static final String[] MenuItemsTitles = { "Hash", "Block" };
	private static MainFrame instance;
	private static Map<String, String[]> menuItemsTitles;
	private HashPanel hashPanel;
	private BlockPanel blockPanel;
	private LinearBlockChainPanel linearBlockChainPanel;
	private TreeBlockChainPanel treeBlockChainPanel;
	
	private static final int hPadding = 15;
	private static final int vPadding = 15;
	private static final String defaultScreen = "Hash";
	
	static {
		menuItemsTitles = new LinkedHashMap<String, String[]>();
		menuItemsTitles.put("Hash", null);
		menuItemsTitles.put("Block", null);
		menuItemsTitles.put("Blockchain", new String[] {"LinkedList", "BinaryTree"});

		//linearBlockChainPanel = new LinearBlockChainPanel(LinearBlockChain.getSampleBlockChain());
	}
	
	public static MainFrame getMainFrameInstance() {
		if (instance == null) {
			instance = new MainFrame();
		}
		return instance;
	}
	private MainFrame() {
		super("BlockChain Demo", 20000, 20000);
		hashPanel = new HashPanel();
		blockPanel = new BlockPanel(LinkedBlock.getValidBlock(), false);
		initFrame();
	}
	
	protected void initFrame() {
		setMenu();
		//setPanels();
		addScreen("Hash", this.hashPanel, false, false);
		addScreen("Block", this.blockPanel, false, false);
		centerToScreen();
		//showScreen(defaultScreen);
	}
	
	private void setPanels() {
		
		
		BorderLayout borderLayout = new BorderLayout();
		//getContentPane().setLayout(borderLayout);
		//hashPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
		
		//add(hashPanel, BorderLayout.CENTER);
		//add(hashPanel, BorderLayout.CENTER);
		//add(blockPanel, BorderLayout.CENTER);
		hashPanel.initPanel();
		hashPanel.setVisible(true);
		blockPanel.initPanel();
		blockPanel.setVisible(true);
		centerToScreen();
		//setLayout(null);
		//add(new JTextArea(40, 40), BorderLayout.SOUTH);
		
	}
	
	public HashPanel getHashPanel() {
		return this.hashPanel;
	}
	
	public void setHashPanel(HashPanel hashPane) {
		this.hashPanel = hashPanel;
	}
	
	public BlockPanel getBlockPanel() {
		return this.blockPanel;
	}
	
	public void setBlockPanel(BlockPanel blockPanel) {
		this.blockPanel = blockPanel;
	}
	
	public void setLinearBlockChainPanel(LinearBlockChainPanel linearBlockChainPanel) {
		this.linearBlockChainPanel = linearBlockChainPanel;
	}
	
	public LinearBlockChainPanel getLinearBlockChainPanel() {
		return this.linearBlockChainPanel;
	}
	
	public TreeBlockChainPanel getTreeBlockChainPanel() {
		return this.treeBlockChainPanel;
	}
	
	public void setTreeBlockChainPanel(TreeBlockChainPanel treeBlockChainPanel) {
		this.treeBlockChainPanel = treeBlockChainPanel;
	}
	
	public void setScreens() {
		
		try {
			if (!super.isScreenAdded("Hash")) {
				addScreen("Hash", this.hashPanel, false, false);
			}
			
			if (!super.isScreenAdded("Block")) {
				addScreen("Block", this.blockPanel, false, false);
			}
			
			if (!super.isScreenAdded("LinkedList")) {
				//this.linearBlockChainPanel.setPreferredSize(new Dimension(400, 400));
				addScreen("LinkedList", this.linearBlockChainPanel, false, true);
			}
			
			if (!super.isScreenAdded("BinaryTree")) {
				addScreen("BinaryTree", this.treeBlockChainPanel, false, true);
			}
		}
		
		
		catch(Exception e) {
			e.printStackTrace();
		}
		
		showScreen(defaultScreen);
	}
	
	private void setMenu() {
		JMenuBar menuBar = new JMenuBar();
		
		for(String menuItemKey : menuItemsTitles.keySet()) {
			String[] subMenues = menuItemsTitles.get(menuItemKey);
			
			if (subMenues == null) {
				super.addMenuItem(menuBar, menuItemKey );
			}
			
			else {
				JMenu menu = new JMenu(menuItemKey);
				for(String subMenuTitle : subMenues) {
					super.addMenuItem(menu, subMenuTitle);
				}
				menuBar.add(menu);
			}
		}
		
//		JMenuItem i1 = new JMenuItem("Hash");
//		JMenuItem i2 = new JMenuItem("Block");
//		JMenuItem i3 = new JMenuItem("LinkedList");
//		JMenuItem i4 = new JMenuItem("BinaryTree");
//		
//		JMenu menu = new JMenu("Blockchain");
//		menu.add(i3);
//		menu.add(i4);
//		
//		menuBar.add(i1);
//		menuBar.add(i2);
//		menuBar.add(menu);
		//menu.setHorizontalAlignment(height);
		menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		//menuBar.add(menu);
		menuBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setJMenuBar(menuBar);
		
	}
}
