package qu.master.blockchain.documentsattestation;

import java.awt.Desktop;
import java.io.File;
import java.util.List;

import qu.master.blockchain.documentsattestation.models.BeansRepository;
import qu.master.blockchain.documentsattestation.models.FilesManager;
import qu.master.blockchain.documentsattestation.models.UsersManager;
import qu.master.blockchain.documentsattestation.models.beans.CryptoModel;
import qu.master.blockchain.documentsattestation.models.beans.Enterprise;
import qu.master.blockchain.documentsattestation.models.beans.SealedDocument;
import qu.master.blockchain.documentsattestation.views.ViewsManager;

/**
 * Hello world!
 *
 */
public class ApplicationMain {
	
	public static void main(String[] args) {
		

		
		
		try {
			//BeansRepository.createDatabase();
			BeansRepository repo = new BeansRepository();
			List<Enterprise> enterprises = repo.getEnterprisesList();

			if (args == null ||args.length == 0 || args[0].equals("C")) {
				UsersManager.setCurrentUserId(AppUtils.getCurrentClient().getId());
				UsersManager.setCurrentClient(AppUtils.getCurrentClient());
				ViewsManager.showClientScreen();
			}
			
			else {
				Enterprise enterprise = enterprises.get(Integer.parseInt(args[1]) - 1);
				UsersManager.setCurrentUserId(enterprise.getId());
				UsersManager.setCurrentEnterprise(enterprise);
		        ViewsManager.showAdminScreen(enterprise.getName());
			}
			AppUtils.emptyDirectory("Temp");
    		
        	
			System.out.println(AppUtils.getRandomId());
			
    		//CryptoModel.generateKeyPair("mypassword", AppUtils.getCurrentClient().getId());
    		//ViewsManager.showClientScreen();
//	        for(Enterprise p : repo.getEnterprisesList().subList(3, 5)) {
//				CryptoModel.generateKeyPair("mypassword", p.getId());
//			}
			
//			SealedDocument doc = repo.getSealedDocuments(enterprises.get(0).getId(), "0acfa6f5-5bee-43ba-8e23-dd2af1d3b9b8").get(0);
//			//System.out.println(doc.getSecretKey());
//			CryptoModel cm = new CryptoModel("mypassword", enterprises.get(0).getId());
//			//CryptoModel cm = new CryptoModel("mypassword", AppUtils.getCurrentClient().getId());
//			byte[] inb = new FilesManager().getFile(doc.getDocumentLocation());
//			String inf = AppUtils.createTempFile();
//			AppUtils.writeBytes(inb, inf);
//			//String ouf = AppUtils.createTempFile();
//			String ouf = "Temp" + File.separator + "test.pdf";
//			cm.decryptData(inb, doc.getSecretKey(), inf, ouf);
//			Desktop.getDesktop().open(new File(ouf));
////			List<Enterprise> enterprises = repo.getEnterprisesList();
////			List<EnterpriseService> services = repo.getEnterprisesServicesList(enterprises.get(1).getId());
////			
////			System.out.println("Enterprises size = " + enterprises.size());
////			System.out.println("Services size = " + services.size());
		}
		
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
