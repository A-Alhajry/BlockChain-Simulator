package qu.master.blockchain.documentsattestation.views;

import java.awt.Component;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {
	
	private Border padding;
	
	public CustomTableCellRenderer(Insets insets) {
		this.padding = BorderFactory.createEmptyBorder(insets.top, insets.left, insets.bottom, insets.right);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		setBorder(BorderFactory.createCompoundBorder(getBorder(), padding));
		return this;
	}
}
