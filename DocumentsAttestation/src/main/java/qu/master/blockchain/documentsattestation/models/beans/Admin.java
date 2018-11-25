package qu.master.blockchain.documentsattestation.models.beans;

public class Admin extends AbstractUser{
	
	private String publicKey;
	private Enterprise enterprise;
	private String fullName;
	
	public Admin() {
		super();
	}
	
	public Admin(String id, Enterprise enterprise, String fullName, String publicKey) {
		super(id);
		this.publicKey = publicKey;
		this.enterprise = enterprise;
		this.fullName = fullName;
	}
	
	public String getPublicKey() {
		return this.publicKey;
	}
	
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	
	public Enterprise getEnterprise() {
		return this.enterprise;
	}
	
	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}
	
	public String getFullname() {
		return this.fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
