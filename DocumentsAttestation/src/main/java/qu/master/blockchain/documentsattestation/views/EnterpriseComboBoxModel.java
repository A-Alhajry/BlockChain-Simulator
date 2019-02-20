package qu.master.blockchain.documentsattestation.views;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import qu.master.blockchain.documentsattestation.models.beans.Enterprise;

public class EnterpriseComboBoxModel extends DefaultComboBoxModel<Enterprise> {
	
	public EnterpriseComboBoxModel(List<Enterprise> enterprises) {
		super(getArray(enterprises));
	}
	
	@Override
	public Enterprise getSelectedItem() {
		return (Enterprise) super.getSelectedItem();
	}
	
	private static Enterprise[] getArray(List<Enterprise> enterprises) {
		Enterprise[] enterpriseArray = new Enterprise[enterprises.size() + 1];
		Enterprise defaultEntr = new Enterprise();
		defaultEntr.setId(null);
		defaultEntr.setName("Please Select");
		enterpriseArray[0] = defaultEntr;
		for(int i = 1; i < enterprises.size() + 1; i++) {
				enterpriseArray[i] = enterprises.get(i - 1);
		}
		return enterpriseArray;
	}
}
