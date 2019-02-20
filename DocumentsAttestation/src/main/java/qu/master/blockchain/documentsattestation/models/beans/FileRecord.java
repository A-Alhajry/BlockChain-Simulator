package qu.master.blockchain.documentsattestation.models.beans;

public class FileRecord {
	
	private String id;
	private String beanId;
	private int typeId;
	private String className;
	private String address;
	
	public FileRecord(String id, String beanId, int typeId, Class<?> beanClass, String address) {
		this(id, beanId, typeId, beanClass.getSimpleName(), address);
	}
	
	public FileRecord(String id, String beanId, int typeId, String className, String address) {
		this.id = id;
		this.beanId = beanId;
		this.typeId = typeId;
		this.className = className;
		this.address = address;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getBeanId() {
		return this.beanId;
	}
	
	public int getTypeId() {
		return this.typeId;
	}
	
	public String getClassName() {
		return this.className;
	}
	
	public String getAddress() {
		return this.address;
	}
}
