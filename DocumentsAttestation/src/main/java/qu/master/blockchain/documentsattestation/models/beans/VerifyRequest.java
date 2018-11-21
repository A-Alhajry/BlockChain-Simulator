package qu.master.blockchain.documentsattestation.models.beans;

import java.time.LocalDateTime;

public class VerifyRequest {
	
	private String id;
	private String userId;
	private String enterpriseId;
	private String contractAddress;
	private LocalDateTime requestTime;
	private Document document;
	private DocumentSignature sign;
	private String documentSignId;
	private RequestStatus status;
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getEnterpriseId() {
		return this.enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
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
	
	public void setDocumentSignId(String documentSignId) {
		this.documentSignId = documentSignId;
	}
	
	public String getDocumentSignId() {
		return this.documentSignId;
	}
	
	public void setStatus(RequestStatus status) {
		this.status = status;
	}
	
	public RequestStatus getStatus() {
		return this.status;
	}
	
}
