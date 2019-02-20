package qu.master.blockchain.documentsattestation.models.beans;

public enum RequestStatus {
	
	IN_PROGRESS(1, "In Progress"),
	ACCEPTED(2, "Accepted"),
	REJECTED(3, "Rejected");
	
	private int id;
	private String name;
	
	private RequestStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name();
	}
	
	public static RequestStatus getStatusById(int id) {
		RequestStatus[] statusList = RequestStatus.values();
		for(RequestStatus status : statusList) {
			if (status.getId() == id) {
				return status;
			}
		}
		
		return statusList[0];
	}
}
