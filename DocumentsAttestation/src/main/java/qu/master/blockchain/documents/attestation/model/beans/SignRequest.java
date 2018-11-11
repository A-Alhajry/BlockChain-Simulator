package qu.master.blockchain.documents.attestation.model.beans;

import java.time.LocalDateTime;

public class SignRequest {
	
	private String id;
	private String userId;
	private String enterpriseId;
	private String serviceId;
	private String documentId;
	private String comments;
	private LocalDateTime requestTime;
	private Document document;
	
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
	
	public String getServiceId() {
		return this.serviceId;
	}
	
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	public String getDocumentId() {
		return this.documentId;
	}
	
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	
	public String getComments() {
		return this.comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
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
	
}
