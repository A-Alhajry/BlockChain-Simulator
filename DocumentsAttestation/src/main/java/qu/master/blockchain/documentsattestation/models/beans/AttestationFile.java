package qu.master.blockchain.documentsattestation.models.beans;

import java.io.Serializable;
import java.util.List;

public class AttestationFile implements Serializable {
	public String DocumentId;
	public String UserSign;
	public List<String> DocumentLocations;
	public List<String> EntrpIds;
	public List<String> EntrpNames;
	public List<String> EntrpSigns;
	public String ContractAddress;
	public List<String> ContractsAddresses;
	
}
