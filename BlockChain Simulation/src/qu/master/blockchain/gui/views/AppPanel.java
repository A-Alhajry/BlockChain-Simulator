package qu.master.blockchain.gui.views;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import qu.master.blockchain.gui.resources.ResourcesLoader;

public abstract class AppPanel extends JPanel{
	
	public JFrame getParentFrame() {
		Object frame = SwingUtilities.getWindowAncestor(this);
		
		if (frame instanceof JFrame) {
			return (JFrame) frame;
		}
		
		else {
			return null;
		}
	}
	
	public void initPanel() {
		
	}
	
	public void setOpacity(float opacity) {
		Color backgroundColor = this.getBackground();
		this.setBackground(new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), (int) (opacity * 100)));
	}
	
	public ImageIcon getLoadingIcon() {
		//Image image = Toolkit.getDefaultToolkit().createImage(new ResourcesLoader().getLoadingIcon());
		try {
			return new ImageIcon(Image.class.getResource("/qu/master/blockchain/gui//resources/loading.gif"));
		}
		
		catch (Exception e) {
			return null;
		}
	}

}
