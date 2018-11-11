package qu.master.blockchain.documentsattestation;

import javax.swing.SwingUtilities;

import qu.master.blockchain.documentsattestation.views.*;

/**
 * Hello world!
 *
 */
public class ApplicationMain {
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
			new ClientMainScreen("Documents Attestation");
		});
		
		
		try {
			//Repository.createDatabase();
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
