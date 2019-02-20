package qu.master.blockchain.documentsattestation.views;

import java.util.List;

import javax.swing.JTable;

import qu.master.blockchain.documentsattestation.AppUtils;
import qu.master.blockchain.documentsattestation.models.beans.DocumentSignature;

public class SignaturesTable extends JTable {
	
	public SignaturesTable(List<DocumentSignature> signs) {
		super(getRows(signs), getCols());
	}
	
	private static Object[] getCols() {
		return new Object[] {"Organization", "Timestamp", "Signature"};
	}
	
	private static Object[][] getRows(List<DocumentSignature> signs) {
		Object[][] rows = new Object[signs.size()][];
		for(int i = 0; i < signs.size(); i++) {
			DocumentSignature sign = signs.get(i);
			Object[] row = new Object[3];
			row[0] = sign.getEnterprise().getName();
			row[1] = AppUtils.formatDate(sign.getTimestamp(), "yyyy-MM-hh MM:hh");
			row[2] = sign.getSign();
			rows[i] = row;
		}
		return rows;
	}
}
