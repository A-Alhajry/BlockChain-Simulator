package qu.master.blockchain.documentsattestation.models;

import qu.master.blockchain.documentsattestation.models.beans.Client;
import qu.master.blockchain.documentsattestation.models.beans.Enterprise;

public class UsersManager {
	
	private static String currentUserId;
	private static Client currentClient;
	private static Enterprise currentEnterprise;
	
	public static void setCurrentUserId(String id) {
		currentUserId = id;
	}
	
	public static String getCurrentUserId() {
		return currentUserId;
	}
	
	public static void setCurrentClient(Client client) {
		currentClient = client;
	}
	
	public static Client getClient() {
		return currentClient;
	}
	
	public static void setCurrentEnterprise(Enterprise enterprise) {
		currentEnterprise = enterprise;
	}
	
	public static Enterprise getCurrentEnterprise() {
		return currentEnterprise;
	}
}
