package qu.master.blockchain.documents.attestation.model.beans;

public class Client {
	
	private String id;
	private String fullName;
	private String publicKey;
	
	
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
}
