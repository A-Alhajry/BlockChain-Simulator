package qu.master.blockchain.documentsattestation.models.beans;

public abstract class AbstractUser {
	
	private String id;
	
	public AbstractUser() {
		
	}
	
	public AbstractUser(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
