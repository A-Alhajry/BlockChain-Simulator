package qu.master.blockchain.documentsattestation.models.beans;

public class EnterpriseService {
	
	private String id;
	private String title;
	private EnterpriseServiceType type;
	private String desc;
	private String[] supportedFiles;

	public EnterpriseService() {
		
	}
	
	public EnterpriseService(String id, String title, String desc, String supportedFiles) {
		this.id = id;
		this.title = title;
		this.desc = desc;
		
		if (supportedFiles != null) {
			this.supportedFiles = supportedFiles.split(",");
		}
	}
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public EnterpriseServiceType getType() {
		return this.type;
	}
	
	public void setType(EnterpriseServiceType type) {
		this.type = type;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String[] getSupportedFiles() {
		return this.supportedFiles;
	}
	
	public void setSupportedFiles(String[] supportedFiles) {
		this.supportedFiles = supportedFiles;
	}
	
	public String getAllowedFilesToString() {
		StringBuilder sb = new StringBuilder();
		for(String fileType : this.supportedFiles) {
			sb.append(fileType + " ");
		}
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return this.title;
	}
}