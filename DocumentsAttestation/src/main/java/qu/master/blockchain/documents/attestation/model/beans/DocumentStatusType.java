package qu.master.blockchain.documents.attestation.model.beans;

import java.time.LocalDateTime;

public enum DocumentStatusType {
	CREATED(1, "Created"),
	SENT_TO_SIGN(2, "Sent To Sign"),
	SIGNED(3, "Signed"),
	SENT_TO_VERIFY(4, "Sent To Verify"),
	VERFIED(5, "Verifed"),
	SIGN_REJECTED(6, "Sign Rejected"),
	VERIFY_REJECTED(7, "Verify Rejected"),
	DELETED(8, "Deleted");	
	
	private int id;
	private String name;
	
	private DocumentStatusType(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name();
	}
}
