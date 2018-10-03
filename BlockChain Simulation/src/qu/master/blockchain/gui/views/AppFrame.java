package qu.master.blockchain.gui.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public abstract class AppFrame extends JFrame {
	
	protected int width = 450;
	protected int height = 450;
	
	private Map<String, AppPanel> titlesToScreens = new HashMap<String, AppPanel>();
	private AppPanel currentScreen;
	private static String noScreenImplementedError = " '%s' is not implemented yet! ";
	private CardLayout pagesControlLayout = new CardLayout();
	
	public AppFrame(String title, int width, int height) {
		super(title);
		this.width = width;
		this.height = height;
		
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(pagesControlLayout);
		getContentPane().setLayout(pagesControlLayout);
		//initFrame();
	}
	
	protected abstract void initFrame();
	
	protected void centerToScreen() {
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		super.setLocation(screenDimension.width/2 - (int) this.getSize().getWidth()/2, screenDimension.height/2 - (int) this.getSize().getHeight()/2);
	}
	
	protected void addScreen(String title, AppPanel screen, boolean display, boolean addScroller) {
		titlesToScreens.put(title, screen);
		screen.setVisible(true);
		JPanel newScreen = new JPanel();
		newScreen.setLayout(new BorderLayout());
		//newScreen.add(screen, BorderLayout.CENTER);
		screen.initPanel();
		newScreen.setVisible(true);
		this.add(newScreen);
		//newScreen.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
		pagesControlLayout.addLayoutComponent(newScreen, title);
		

		if (addScroller) {
			//screen.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
			JScrollPane scroller = new JScrollPane(screen);
			newScreen.add(scroller, BorderLayout.CENTER);
			//scroller.setViewportView(screen);
			//add(scroller);
		}
		
		else {
			newScreen.add(screen, BorderLayout.CENTER);
		}
		
		if (display) {
			//pagesControlLayout.show(getContentPane(), title);
			//newScreen.setVisible(true);
		}
		
	}
	
	protected boolean isScreenAdded(String title) {
		return titlesToScreens.containsKey(title);
	}
	
	protected void addMenuItem(JMenuBar menuBar, String title) {
		menuBar.add(new JMenuItem(new AbstractAction(title) {
			public void actionPerformed(ActionEvent e) {
				showScreen(title);
			}
		}));
	}
	
	protected void addMenuItem(JMenu menu, String title) {
		menu.add(new JMenuItem(new AbstractAction(title) {
			public void actionPerformed(ActionEvent e) {
				showScreen(title);
			}
		}));
	}
	
	protected void showScreen(String title) {
		
		if (!titlesToScreens.containsKey(title)) {
			JOptionPane.showMessageDialog(this, String.format(noScreenImplementedError, title), "Info", JOptionPane.INFORMATION_MESSAGE);;
			return;
		}
		
		AppPanel screen = titlesToScreens.get(title);
		//screen.removeAll();
		//screen.initPanel();
		pagesControlLayout.show(getContentPane(), title);
//		
//		for(String key : titlesToScreens.keySet()) {
//			AppPanel screen = titlesToScreens.get(key);
//			if (title.equals(key)) {
////				MainFrame.getMainFrameInstance().add(screen);
////				screen.setVisible(true);
////				screen.initPanel();
//				//add(screen);
//			}
//			
//			else {
////				MainFrame.getMainFrameInstance().remove(screen);
////				screen.setVisible(false);
//				//remove(screen);
//			}
//		}
	}
	
	
	
	
}
