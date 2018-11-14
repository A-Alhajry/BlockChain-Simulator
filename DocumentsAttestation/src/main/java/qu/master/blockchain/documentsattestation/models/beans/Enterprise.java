package qu.master.blockchain.documentsattestation.models.beans;

public class Enterprise {
	
	private String id;
	private String name;
	private String publicKey;
	
	public Enterprise() {
		
	}
	
	public Enterprise(String id, String name, String publicKey) {
		this.id = id;
		this.name = name;
		this.publicKey = publicKey;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPublicKey() {
		return this.publicKey;
	}
	
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
}
