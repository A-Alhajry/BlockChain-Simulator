package qu.master.blockchain.documentsattestation.models.beans;

public class Client {
	
	private String id;
	private String fullName;
	private String publicKey;
	private String username;
	private String password;
	
	public Client() {
		
	}
	
	public Client(String id, String fullName, String publicKey) {
		this.id = id;
		this.fullName = fullName;
		this.publicKey = publicKey;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFullName() {
		return this.fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getPublicKey() {
		return this.publicKey;
	}
	
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
}
