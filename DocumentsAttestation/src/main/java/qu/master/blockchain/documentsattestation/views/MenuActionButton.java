package qu.master.blockchain.documentsattestation.views;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class MenuActionButton extends AbstractAction {
	
	private String title;
	private String name;
	private Container container;
	private CardLayout layout;
	
	public MenuActionButton(String title, String name, Container container, CardLayout layout) {
		super(title);
		this.title = title;
		this.name = name;
		this.container = container;
		this.layout = layout;
	}
	
	public void actionPerformed(ActionEvent e) {
		layout.show(container, name);
	}
	
	
}
