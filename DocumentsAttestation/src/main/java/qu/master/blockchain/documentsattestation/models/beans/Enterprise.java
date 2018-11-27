package qu.master.blockchain.documentsattestation.models.beans;

public class Enterprise {
	
	private String id;
	private String name;
	private String shortName;
	private String publicKey;
	
	public Enterprise() {
		
	}
	
	public Enterprise(String id, String name, String shortName, String publicKey) {
		this.id = id;
		this.name = name;
		this.shortName = shortName;
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
	
	public String getShortName() {
		return this.shortName;
	}
	
	public void setShortName(String shortName) {
		this.shortName = shortName;
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
