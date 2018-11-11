package qu.master.blockchain.documents.attestation.model.beans;

import java.time.LocalDateTime;

public class SignResponse {
	
	private String id;
	private boolean status;
	private DocumentSignature sign;
	private Document document;
	private LocalDateTime responseTime;
	private String contractAddress;
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public boolean getStatus() {
		return this.status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public DocumentSignature getSign() {
		return this.sign;
	}
	
	public void DocumentSignature(DocumentSignature sign) {
		this.sign = sign;
	}
	
	public Document getDocument() {
		return this.document;
	}
	
	public void setDocument(Document document) {
		this.document = document;
	}
	
	public LocalDateTime getResponseTime() {
		return this.responseTime;
	}
	
	public void setResponseTime(LocalDateTime responseTime) {
		this.responseTime = responseTime;
	}
	
	public String getContractAddress() {
		return this.contractAddress;
	}
	
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	
	
	
}

