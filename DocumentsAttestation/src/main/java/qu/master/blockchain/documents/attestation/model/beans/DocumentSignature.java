package qu.master.blockchain.documents.attestation.model.beans;

import java.time.LocalDateTime;

public class DocumentSignature {
	
	private String sign;
	private Enterprise enterprise;
	private LocalDateTime timestamp;
	
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
