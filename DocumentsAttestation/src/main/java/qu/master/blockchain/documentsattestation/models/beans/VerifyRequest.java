package qu.master.blockchain.documentsattestation.models.beans;

import java.time.LocalDateTime;

public class VerifyRequest {
	
	private String id;
	private String userId;
	private String enterpriseId;
	private String serviceId;
	private String contractAddress;
	private LocalDateTime requestTime;
	private Document document;
	private DocumentSignature sign;
	private String documentId;
	private String documentSignId;
	private RequestStatus status;
	private String documentLocation;
	private Client client;
	private Enterprise enterprise;
	private EnterpriseService service;
	private Boolean isDocumentValid;
	
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
	
	public String getDocumentId() {
		return this.documentId;
	}
	
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
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
	
	public String getDocumentLocation() {
		return this.documentLocation;
	}
	
	public void setDocumentLocation(String documentLocation) {
		this.documentLocation = documentLocation;
	}
	
	public Client getClient() {
		return this.client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public Enterprise getEnterprise() {
		return this.enterprise;
	}
	
	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}
	
	public EnterpriseService getService() {
		return this.service;
	}
	
	public void setService(EnterpriseService service) {
		this.service = service;
	}
	
	public Boolean isDocumentValid() {
		return this.isDocumentValid;
	}
	
	public void setIsDocumentValid(Boolean isDocumentValid) {
		this.isDocumentValid = isDocumentValid;
	}
	
}
