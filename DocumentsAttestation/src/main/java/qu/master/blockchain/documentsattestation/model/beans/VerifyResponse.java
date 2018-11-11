package qu.master.blockchain.documentsattestation.model.beans;

import java.time.LocalDateTime;

public class VerifyResponse {
	
	private String id;
	private LocalDateTime responseTime;
	private boolean status;
	private DocumentSignature sign;
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public LocalDateTime getResponseTime() {
		return this.responseTime;
	}
	
	public void setResponseTime(LocalDateTime responseTime) {
		this.responseTime = responseTime;
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
	
	public void setSign(DocumentSignature sign) {
		this.sign = sign;
	}
}
