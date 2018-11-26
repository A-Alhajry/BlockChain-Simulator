package qu.master.blockchain.documentsattestation.models.beans;

import java.time.LocalDateTime;

public class DocumentSignature {
	
	private String id;
	private String sign;
	private Enterprise enterprise;
	private LocalDateTime timestamp;
	
	public DocumentSignature(String id, String sign, Enterprise enterprise, LocalDateTime timestamp) {
		this.id = id;
		this.sign = sign;
		this.enterprise = enterprise;
		this.timestamp = timestamp;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSign() {
		return this.sign;
	}
	
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public Enterprise getEnterprise() {
		return this.enterprise;
	}
	
	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}
	
	public LocalDateTime getTimestamp() {
		return this.timestamp;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
