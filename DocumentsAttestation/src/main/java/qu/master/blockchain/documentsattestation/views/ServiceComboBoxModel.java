package qu.master.blockchain.documentsattestation.views;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import qu.master.blockchain.documentsattestation.models.beans.EnterpriseService;

public class ServiceComboBoxModel extends DefaultComboBoxModel<EnterpriseService> {
	
	public ServiceComboBoxModel(List<EnterpriseService> services) {
		super(getArray(services));
	}
	
	@Override
	public EnterpriseService getSelectedItem() {
		return (EnterpriseService) super.getSelectedItem();
	}
	
	private static EnterpriseService[] getArray(List<EnterpriseService> services) {
		EnterpriseService[] servicesArray = new EnterpriseService[services.size() + 1];
		EnterpriseService defaultSrv = new EnterpriseService(null, "Please Select", null, null);
		servicesArray[0] = defaultSrv;
		for(int i = 1; i < services.size() + 1; i++) {
			servicesArray[i] = services.get(i - 1);
		}
		return servicesArray;
	}
}
