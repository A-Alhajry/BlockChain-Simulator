package qu.master.blockchain.documentsattestation.model.beans;

public class ContractInput {
	
	private String clientKey;
	private String enterpriseKey;
	private String clientSign;
	private String enterpriseSign;
	private String documentHash;
	private String documentAddress;
	
	public String getClientKey() {
		return this.clientKey;
	}
	
	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
	
	public String getEnterpriseKey() {
		return enterpriseKey;
	}
	
	public void setEnterpriseKey(String enterpriseKey) {
		this.enterpriseKey = enterpriseKey;
	}
	
	public String getClientSign() {
		return this.clientSign;
	}
	
	public void setClientSign(String clientSign) {
		this.clientSign = clientSign;
	}
	
	public String getEnterpriseSign() {
		return this.enterpriseSign;
	}
	
	public String getDocumentHash() {
		return this.documentHash;
	}
	
	public void setDocumentHash(String documentHash) {
		this.documentHash = documentHash;
	}
	
	public String getDocumentAddress() {
		return this.documentAddress;
	}
	
	public void setDocumentAddress(String documentAddress) {
		this.documentAddress = documentAddress;
	}
}
