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
import qu.master.blockchain.documentsattestation.models.beans.RequestStatus;
import qu.master.blockchain.documentsattestation.models.beans.SignRequest;

public class ClientSignRequestsTable extends JTable {

	private Object[] cols;
	private Object[][] rows;

	private List<SignRequest> requests;

	private static final int signsCol = 3;
	private static final int statusCol = 4;
	private static final int saveCol = 5;
	
	private Consumer<SignRequest> signsAction;
	private Consumer<SignRequest> saveAction;
	
	public ClientSignRequestsTable(List<SignRequest> requests, Consumer<SignRequest> saveAction, Consumer<SignRequest> signsAction) {
		super(getRows(requests), getCols());
		this.saveAction = saveAction;
		this.signsAction = signsAction;
		this.requests = requests;
		setEvents();
	}

//	@Override
//	public String getColumnName(int col) {
//		return cols[col].toString();
//	}
//
//	@Override
//	public int getColumnCount() {
//		return this.cols.length;
//	}
//
//	@Override
//	public int getRowCount() {
//		return this.rows.length;
//	}
//
//	@Override
//	public Object getValueAt(int row, int col) {
//		return this.rows[row][col];
//	}
//	
	@Override
	public TableCellRenderer getCellRenderer(int row, int col) {
		return new ClientVerifyRequestsTableRenderer(statusCol, saveCol);
	}
	
	private void setEvents() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent ev) {
				int row = rowAtPoint(ev.getPoint());
				int col = ClientSignRequestsTable.this.columnAtPoint(ev.getPoint());
				SignRequest req = requests.get(row);
				if (saveAction != null && col == saveCol) {
					saveAction.accept(req);
				}
				
				if (signsAction != null && col == signsCol) {
					signsAction.accept(req);
				}
			}
		});
	}

	private static Object[] getCols() {
		Object[] cols = new String[] { "Organization", "Service", "Date", "Signatures", "Status", "Actions" };
		return cols;
	}

	private static Object[][] getRows(List<SignRequest> requests) {
		Object[][] rows = new Object[requests.size()][];
		for (int i = 0; i < requests.size(); i++) {
			SignRequest request = requests.get(i);
			List<DocumentSignature> signs = request.getDocument().getSignHistory();
			Object[] col = new Object[6];
			col[0] = request.getEnterprise().getName();
			col[1] = request.getService().getTitle();
			col[2] = AppUtils.formatDate(request.getRequestTime(), "yyyy-MM-dd HH-mm");
			col[3] = signs == null ? "" : signs.stream().map((d) -> d.getEnterprise().getShortName()).collect(Collectors.joining(", "));
			col[4] = request.getStatus().getName();
			col[5] = "Save";
			rows[i] = col;
		}
		return rows;
	}
}

class ClientSignRequestsTableRenderer extends JPanel implements TableCellRenderer {

	private int statusCol;
	private int saveCol;

	public ClientSignRequestsTableRenderer(int statusCol, int saveCol) {
		this.statusCol = statusCol;
		this.saveCol = saveCol;
	}

	@Override
	public Component getTableCellRendererComponent(final JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int col) {
		this.setBackground(Color.WHITE);
		JPanel cellPanel = new JPanel();
		cellPanel.setBackground(Color.WHITE);
		String cellValue = value.toString();
		if (col <= statusCol) {
			JLabel text = new JLabel(cellValue);
			if (col == statusCol) {
				Color cellColor;
				Color textColor;
				if (cellValue.equals(RequestStatus.ACCEPTED.getName())) {
					cellColor = Color.GREEN;
					textColor = Color.WHITE;
				}

				else if (cellValue.equals(RequestStatus.REJECTED.getName())) {
					cellColor = Color.RED;
					textColor = Color.WHITE;
				}

				else {
					cellColor = cellPanel.getBackground();
					textColor = text.getForeground();
				}
				
				cellPanel.setBackground(cellColor);
				text.setForeground(textColor);
			}
			
			cellPanel.add(text);
		}
		
		else {
			JButton button = new JButton(cellValue);
			cellPanel.add(button);
		}
		
		this.add(cellPanel);
		return this;

	}
}
