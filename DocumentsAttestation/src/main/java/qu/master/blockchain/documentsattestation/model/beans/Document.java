package qu.master.blockchain.documentsattestation.model.beans;

import java.time.LocalDateTime;
import java.util.List;

public class Document {
	
	private String id;
	private String title;
	private LocalDateTime creationTime;
	private byte[] rawFile;
	private String hash;
	private String userSign;
	private Client owner;
	private List<DocumentStatus> statusHistory;
	private List<DocumentSignature> signHistory;
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public LocalDateTime getCreationTime() {
		return this.creationTime;
	}
	
	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}
	
	public String getHash() {
		return this.hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public byte[] getRawFile() {
		return this.rawFile;
	}
	
	public void setRawFile(byte[] rawFile) {
		this.rawFile = rawFile;
	}

	public Client getOwner() {
		return this.owner;
	}
	
	public String getUserSign() {
		return this.userSign;
	}
	
	public void setUserSign(String userSign) {
		this.userSign = userSign;
	}
	
	public void setOwner(Client owner) {
		this.owner = owner;
	}
	
	public List<DocumentStatus> getStatusHistory() {
		return this.statusHistory;
	}
	
	public void setStatusHistory(List<DocumentStatus> statusHistory) {
		this.statusHistory = statusHistory;
	}

	public List<DocumentSignature> getSignHistory() {
		return this.signHistory;
	}
	
	public void setSignHistory(List<DocumentSignature> signHistory) {
		this.signHistory = signHistory;
	}
}