package qu.master.blockchain.documentsattestation.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class AdminMainScreen extends JFrame {
	
	private static final CardLayout screensLayout = new CardLayout();
	
	
	public AdminMainScreen(String title, int width, int height) {
		super(title);
		super.setSize(width, height);
		super.setPreferredSize(new Dimension(width, height));
		super.setLocationRelativeTo(null);
		super.setVisible(true);
		super.pack();
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(screensLayout);
		setMenues();
	}
	
	private void setMenues() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(new JMenuItem(new MenuActionButton("Signatures", "AS", getContentPane(), screensLayout)));
		menuBar.add(new JMenuItem(new MenuActionButton("Verifications", "AV", getContentPane(), screensLayout)));
		
		menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		menuBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		setJMenuBar(menuBar);
		addScreen(new AdminViewSignsScreen(), "AS");
		addScreen(new AdminViewVerificationsScreen(), "AV");
	}
	
	private void addScreen(AbstractScreen screen, String screenId) {
		JPanel screenContainer = new JPanel();
		screenContainer.setLayout(new BorderLayout());
		screenContainer.add(screen, BorderLayout.CENTER);
		add(screenContainer);
		screensLayout.addLayoutComponent(screenContainer, screenId);
	}
}
