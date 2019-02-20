package qu.master.blockchain.documentsattestation.models.beans;

public enum EnterpriseServiceType {
	SIGN(1, "Sign Service"),
	VERIFY(2, "Verify Service");
	
	private int id;
	private String name;
	
	private EnterpriseServiceType(int id, String name) {
		this.id = id;
		this.name= name;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
}