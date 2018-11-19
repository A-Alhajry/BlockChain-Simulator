package qu.master.blockchain.documentsattestation;

import javax.swing.SwingUtilities;

import qu.master.blockchain.documentsattestation.models.BeansRepository;
import qu.master.blockchain.documentsattestation.models.beans.CryptoModel;
import qu.master.blockchain.documentsattestation.views.ClientMainScreen;
import qu.master.blockchain.documentsattestation.views.ViewsManager;

/**
 * Hello world!
 *
 */
public class ApplicationMain {
	
	public static void main(String[] args) {
		
		CryptoModel.generateKeyPair("mypassword");
		ViewsManager.showClientScreen();
		
		
		try {
//			BeansRepository.createDatabase();
//			Repository repo = new Repository();
//			List<Enterprise> enterprises = repo.getEnterprisesList();
//			List<EnterpriseService> services = repo.getEnterprisesServicesList(enterprises.get(1).getId());
//			
//			System.out.println("Enterprises size = " + enterprises.size());
//			System.out.println("Services size = " + services.size());
		}
		
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
