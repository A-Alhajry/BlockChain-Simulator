package qu.master.blockchain.documentsattestation.models.beans;

import java.time.LocalDateTime;

public class SealedDocument {
	
	private String id;
	private String documentId;
	private String documentLocation;
	private String partyId;
	private String partyType;
	private String secretKey;
	private String documentTitle;
	private LocalDateTime timestamp;
	
	public SealedDocument() {
		
	}
	
	public SealedDocument(String id, String documentId, String documentTitle, String documentLocation, String partyId, String partyType, String secretKey, LocalDateTime timestamp) {
		this.id = id;
		this.documentId = documentId;
		this.documentTitle = documentTitle;
		this.documentLocation = documentLocation;
		this.partyId = partyId;
		this.partyType = partyType;
		this.secretKey = secretKey;
		this.timestamp = timestamp;
	}
	
    public String getId() {
		return this.id;
	}
    
    public void setId(String id) {
    	this.id = id;
    }
     
    public String getDocumentId() {
    	return this.documentId;
    }
    
    public void setDocumentId(String documentId) {
    	this.documentId = documentId;
    }
    
    public String getDocumentLocation() {
    	return documentLocation;
    }
    
    public void setDocumentLocation(String documentId) {
    	this.documentId = documentId;
    }
    
    public String getPartyId() {
    	return this.partyId;
    }
    
    public void setPartyId(String partyId) {
    	this.partyId = partyId;
    }
    
    public String getPartyType() {
    	return this.partyType;
    }
    
    public void setPartyType(String partyType) {
    	this.partyType = partyType;
    }
    
    public String getSecretKey() {
    	return this.secretKey;
    }
    
    public void setSecretKey(String secretKey) {
    	this.secretKey = secretKey;
    }
	
	public String getDocumentTitle() {
		return this.documentTitle;
	}
	
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
	
	public LocalDateTime getTimestamp() {
		return this.timestamp;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	
}