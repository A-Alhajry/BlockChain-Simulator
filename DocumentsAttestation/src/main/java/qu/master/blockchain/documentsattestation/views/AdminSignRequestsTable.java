package qu.master.blockchain.documentsattestation.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import qu.master.blockchain.documentsattestation.AppUtils;
import qu.master.blockchain.documentsattestation.models.beans.DocumentSignature;
import qu.master.blockchain.documentsattestation.models.beans.SignRequest;

public class AdminSignRequestsTable extends JTable {
	
	private static String[] cols;
	private static String[][] rows;
	
	public static int signsCol;
	public static int statusCol;
	public static int openCol;
	public static int actionCol;
	
	private List<SignRequest> requests;
	private Consumer<SignRequest> signsAction;
	private Consumer<SignRequest> openAction;
	private Consumer<SignRequest> selectAction;
	
	public AdminSignRequestsTable(List<SignRequest> requests) {
		super(initRows(requests), initCols());
		this.requests = requests;
		setEvents();
	}
	
	@Override
	public TableCellRenderer getCellRenderer(int row, int col) {
		return new AdminSignRequestsCellRenderer();
	}
	
	public String[] getCols() {
		return this.cols;
	}
	
	public String[][] getRows() {
		return this.rows;
	}
	
	public void setOpenAction(Consumer<SignRequest> action) {
		this.openAction = action;
	}
	
	public void setSelectAction(Consumer<SignRequest> action) {
		this.selectAction = action;
	}
	
	public void setSignsAction(Consumer<SignRequest> action) {
		this.signsAction = action;
	}
	
	public List<SignRequest> getRequests() {
		return this.requests;
	}
	
	private void setEvents() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent ev) {
				int row = AdminSignRequestsTable.this.rowAtPoint(ev.getPoint());
				int col = AdminSignRequestsTable.this.columnAtPoint(ev.getPoint());
				
				SignRequest selectedRequest = AdminSignRequestsTable.this.getRequests().get(row);
				if (col == openCol && openAction != null) {
					openAction.accept(selectedRequest);
				}
				
				else if (col == actionCol && selectAction != null) {
					selectAction.accept(selectedRequest);
				}
				
				else if (col == signsCol && selectAction != null) {
					signsAction.accept(selectedRequest);
				}
			}
		});
	}
	
	 
	private static String[] initCols() {
		cols = new String[] {"Client", "Service", "Date", "Signatures", "Status", "File", "Action"};
		return cols;
	}
	
	private static String[][] initRows(List<SignRequest> requests) {
		rows = new String[requests.size()][];
		for(int i = 0;i < requests.size(); i++) {
			SignRequest request = requests.get(i);
			List<DocumentSignature> signs = request.getDocument().getSignHistory();
			String[] row = new String[7];
			row[0] = request.getClient().getFullName();
			row[1] = request.getService().getTitle();
			row[2] = AppUtils.formatDate(request.getRequestTime(), "yyyy-MM-dd HH:mm");
			row[3] = signs == null ? "" : signs.stream().map((d) -> d.getEnterprise().getShortName()).collect(Collectors.joining(", "));
			row[4] = request.isDocumentValid()? "Valid Document" : "Corrupted Document";
			row[5] = "Open";
			row[6] = "Select";
			rows[i] = row;
			
			statusCol = 4;
			openCol = 5;
			actionCol = 6;
		}
		
		return rows;
	}
} 


class AdminSignRequestsCellRenderer extends JPanel implements TableCellRenderer {
	
	@Override
	public Component getTableCellRendererComponent(final JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		
		String cell = value.toString();
		this.setBackground(Color.WHITE);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		//this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
		if (col <= AdminSignRequestsTable.statusCol) {
			//this.setForeground(Color.RED);
			JLabel text = new JLabel();
			text.setText(cell);
			
			if (col == AdminSignRequestsTable.statusCol) {
				Color color = cell.contains("Valid") ? Color.GREEN : Color.RED;
				panel.setBackground(color);
				text.setForeground(Color.WHITE);
			}
			panel.add(text);
		}
		
		else {
			JButton button = new JButton(cell);
			//panel.setBackground(Color.BLUE);
			//button.setForeground(Color.WHITE);
			panel.add(button);
		}
		
		
		this.add(panel);
		return this;
	}
}