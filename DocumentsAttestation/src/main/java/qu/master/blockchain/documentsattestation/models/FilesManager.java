package qu.master.blockchain.documentsattestation.models;

import java.io.File;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import qu.master.blockchain.documentsattestation.AppUtils;

public class FilesManager {
	
	private String url;
	
	public FilesManager() {
		this.url = "/ip4/127.0.0.1/tcp/5001";
	}
	
	public FilesManager(String url) {
		this.url = url;
	}
	
	public String addFile(File file) {
		try {
			IPFS ipfs = new IPFS(this.url);
			ipfs.refs.local();
			NamedStreamable.FileWrapper fileWrapper = new NamedStreamable.FileWrapper(file);
			MerkleNode fileNode = ipfs.add(fileWrapper).get(0);
			System.out.println("File Address = " + fileNode.hash.toBase58());
			return fileNode.hash.toBase58();
		}
		
		catch (Exception e) {
			throw AppUtils.getError(e);
		}
	}
	
	public byte[] getFile(String address) {
		try {
			IPFS ipfs = new IPFS(this.url);
			ipfs.refs.local();
			Multihash filePointer = Multihash.fromBase58(address);
			return ipfs.cat(filePointer);
			
		}
		
		catch (Exception e) {
			throw AppUtils.getError(e);
		}
	}
}
