package qu.master.blockchain.documentsattestation.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class ClientMainScreen extends JFrame {
	
	private static int width = 1000;
	private static int height = 1000;
	
	private CardLayout layout = new CardLayout();
	
	public ClientMainScreen(String title) {
		super(title);
		super.setSize(width, height);
		super.setPreferredSize(new Dimension(width, height));
		super.setVisible(true);
		super.pack();
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setLocationRelativeTo(null);
		setLayout(layout);
		
		SignRequestScreen signRequestScreen = new SignRequestScreen();
		ViewSignsScreen viewSignsScreen = new ViewSignsScreen();
		VerifyRequestScreen verifyRequestScreen = new VerifyRequestScreen();
		ViewVerificationsScreen viewVerScreen = new ViewVerificationsScreen();
		
		JPanel signRequestPanel = new JPanel();
		JPanel signViewPanel = new JPanel();
		JPanel verifyRequestPanel = new JPanel();
		JPanel verViewPanel = new JPanel();
		
		signRequestPanel.setLayout(new BorderLayout());
		signRequestPanel.add(signRequestScreen, BorderLayout.CENTER);
		
		signViewPanel.setLayout(new BorderLayout());
		signViewPanel.add(viewSignsScreen, BorderLayout.CENTER);
		
		verifyRequestPanel.setLayout(new BorderLayout());
		verifyRequestPanel.add(verifyRequestScreen, BorderLayout.CENTER);
		
		verViewPanel.setLayout(new BorderLayout());
		verViewPanel.add(viewVerScreen, BorderLayout.CENTER);
		
		
		
		add(signRequestPanel);
		add(signViewPanel);
		add(verifyRequestPanel);
		add(verViewPanel);
		
		layout.addLayoutComponent(signRequestPanel, "SRF");
		layout.addLayoutComponent(signViewPanel, "SRT");
		layout.addLayoutComponent(verifyRequestPanel, "VRF");
		layout.addLayoutComponent(verViewPanel, "VRT");
		
		setMenu();
	}
	
	private void setMenu() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu signMenu = new JMenu("Sign");
		signMenu.add(new JMenuItem(new MenuActionButton("New Request", "SRF", getContentPane(), layout)));
		signMenu.add(new JMenuItem(new MenuActionButton("View List", "SRT", getContentPane(), layout)));
		
		JMenu verifyMenu = new JMenu("Verify");
		verifyMenu.add(new JMenuItem(new MenuActionButton("New Request", "VRF", getContentPane(), layout)));
		verifyMenu.add(new JMenuItem(new MenuActionButton("View List", "VRT", getContentPane(), layout)));
		
		menuBar.add(signMenu);
		menuBar.add(verifyMenu);
		
		menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		menuBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		setJMenuBar(menuBar);
	}
	
	private void showScreen(String title) {
		layout.show(getContentPane(), title);
	}
	
	
}
