package qu.master.blockchain.documentsattestation.models.beans;

import java.time.LocalDateTime;

public class SignRequest {
	
	private String id;
	private String userId;
	private String enterpriseId;
	private String serviceId;
	private String documentId;
	private String contractAddress;
	private String comments;
	private RequestStatus status;
	private LocalDateTime requestTime;
	private Client client;
	private Document document;
	private DocumentStatus documentStatus;
	private Enterprise enterprise;
	private EnterpriseService service;
	
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
	
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	
	public String getContractAddress() {
		return this.contractAddress;
	}
	
	public String getComments() {
		return this.comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public void setStatus(RequestStatus status) {
		this.status = status;
	}
	
	public RequestStatus getStatus() {
		return this.status;
	}
	
	public LocalDateTime getRequestTime() {
		return this.requestTime;
	}
	
	public void setRequestTime(LocalDateTime requestTime) {
		this.requestTime = requestTime;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public Client getClient() {
		return this.client;
	}
	public Document getDocument() {
		return this.document;
	}
	
	public void setDocument(Document document) {
		this.document = document;
	}
	
	public void setDocumentStatus(DocumentStatus documentStatus) {
		this.documentStatus = documentStatus;
	}
	
	public DocumentStatus getDocumentStatus() {
		return this.documentStatus;
	}
	
	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}
	
	public Enterprise getEnterprise() {
		return this.enterprise;
	}
	
	public void setService(EnterpriseService service) {
		this.service = service;
	}
	
	public EnterpriseService getService() {
		return this.service;
	}
	
}
