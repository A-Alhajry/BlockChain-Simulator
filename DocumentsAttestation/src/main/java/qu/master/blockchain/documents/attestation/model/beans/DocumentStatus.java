package qu.master.blockchain.documents.attestation.model.beans;

import java.time.LocalDateTime;

public class DocumentStatus {
	
	private String id;
	private DocumentStatusType type;
	private LocalDateTime statusTime;
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public DocumentStatusType getType() {
		return this.type;
	}
	
	public void setType(DocumentStatusType type) {
		this.type = type;
	}
	
	public LocalDateTime getStatusTime() {
		return statusTime;
	}
	
	public void setStatusTime(LocalDateTime statusTime) {
		this.statusTime = statusTime;
	}
}
