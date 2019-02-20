package qu.master.blockchain.documentsattestation.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

public abstract class AbstractScreen extends JPanel {
	
	public void initScreen() {
		
	}
	
	protected void addHeader(JPanel parent, JLabel label, Font font) {
		
		label.setFont(font);
		parent.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.weightx = 1;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		parent.add(label, gbc);
	}
	protected void addControl(JPanel parent, JLabel label, JTextComponent input, Insets insets, Dimension labelSize, Dimension inputSize) {
		
		parent.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.WEST;
		gbc.insets = insets;
		gbc.ipady = (int) label.getHeight();
		
		label.setPreferredSize(labelSize);
		parent.add(label, gbc);
		
		gbc.gridx = 1;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.VERTICAL;
		
		input.setPreferredSize(inputSize);
		input.setMinimumSize(inputSize);
		parent.add(input, gbc);
		
	}
	
	protected void addControl(JPanel parent, JLabel label, JComboBox<?> list, Insets insets, Dimension labelSize, Dimension listSize) {
		
		parent.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.WEST;
		gbc.insets = insets;
		
		label.setPreferredSize(labelSize);
		parent.add(label, gbc);
		
		gbc.gridx = 1;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.VERTICAL;
		
		list.setPreferredSize(listSize);
		list.setMinimumSize(listSize);
		parent.add(list, gbc);
	}
	
	protected void addTable(JPanel parent, String[] header, String[][] body, Insets insets, Font headerFont, Font bodyFont) {
		JTable table = new JTable(body, header);
		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		addTable(parent, table, insets, headerFont, bodyFont);
		//table.setModel(model);
		
		
	}
	
	protected void addTable(JPanel parent, JTable table, Insets insets, Font headerFont, Font bodyFont) {
		JScrollPane sp = new JScrollPane(table);
		int rowHeight = 35;
		Dimension tableSize = new Dimension((int) table.getPreferredSize().getWidth(), (int) table.getPreferredSize().getHeight() + rowHeight * (table.getRowCount() - 1));
		sp.setPreferredSize(new Dimension((int) tableSize.getWidth(), (int) 0 + rowHeight * (table.getRowCount() + 1)));
		table.setRowHeight(rowHeight);
		table.setPreferredScrollableViewportSize(tableSize);
		table.setFillsViewportHeight(true);
		table.setFont(bodyFont);
		table.getTableHeader().setFont(headerFont);
		parent.setLayout(new BorderLayout());
		parent.add(sp);
		
//		table.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				int row = table.rowAtPoint(e.getPoint());		
//				int col = table.columnAtPoint(e.getPoint());
//				
//				System.out.println("col = " + col + " row = " + row);
//			}
//		});
	}
	
	
	protected void addRow(JPanel parent, JPanel row, Insets insets, int gridx, int gridy) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = insets;
		parent.add(row, gbc);
	}
	
	protected Font getFont(int style, int size) {
		Font font = new JLabel().getFont();
		return new Font(font.getFontName(), style, size);
	}
	
	protected Font getPlainFont(int fontSize) {
		return getFont(Font.PLAIN, fontSize);
	}
	
	protected Font getBoldFont(int fontSize) {
		return getFont(Font.BOLD, fontSize);
	}
	
	protected Font getItalicFont(int fontSize) {
		return getFont(Font.ITALIC, fontSize);
	}
	
	protected GridBagConstraints getDefaultConstraints(Insets insets) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = insets;
		return gbc;
	}
	
	public void showDialog(String title, String message, int dialogType) {
		JOptionPane.showMessageDialog(this, message, title, dialogType);
	}
	
	public String showInputDialog(String message) {
		return JOptionPane.showInputDialog(this, message);
	}
}
