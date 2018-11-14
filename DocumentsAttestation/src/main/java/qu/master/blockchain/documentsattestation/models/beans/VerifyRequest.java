package qu.master.blockchain.documentsattestation.models.beans;

import java.time.LocalDateTime;

public class VerifyRequest {
	
	private String id;
	private String contractAddress;
	private LocalDateTime requestTime;
	private Document document;
	private DocumentSignature sign;
	
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getContractAddress() {
		return this.contractAddress;
	}
	
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	
	public LocalDateTime getRequestTime() {
		return this.requestTime;
	}
	
	public void setRequestTime(LocalDateTime requestTime) {
		this.requestTime = requestTime;
	}
	
	public Document getDocument() {
		return this.document;
	}
	
	public void setDocument(Document document) {
		this.document = document;
	}
	
	public DocumentSignature getSign() {
		return this.sign;
	}
	
	public void setDocumentSignature(DocumentSignature sign) {
		this.sign = sign;
	}
	
	
}
