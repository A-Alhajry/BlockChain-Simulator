package qu.master.blockchain.gui.views;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class ControlWrapper<T extends JComponent> extends JPanel {
	
	private T control;
	
	public ControlWrapper(T control) {
		super();
		this.control = control;
		setLayout(new BorderLayout());
		add(control, BorderLayout.NORTH);
		//setBorder(BorderFactory.createLineBorder(Color.PINK, 5));
		//setLayout(new BorderLayout());
	}
	
	public T getControl() {
		return this.control;
	}
	
	public int getWidth() {
		return (int) this.control.getSize().getWidth();
	}
	
	public int getHeight() {
		return (int) this.control.getSize().getHeight();
	}
	
//	@Override
//	public Dimension getSize() {
//		return this.control.getSize();
//	}
//	
//	@Override
//	public Dimension getPreferredSize() {
//		return this.control.getPreferredSize();
//	}
}
