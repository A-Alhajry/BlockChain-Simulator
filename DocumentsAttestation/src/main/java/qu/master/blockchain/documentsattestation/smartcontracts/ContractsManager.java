package qu.master.blockchain.documentsattestation.smartcontracts;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import qu.master.blockchain.documentsattestation.AppUtils;
import qu.master.blockchain.documentsattestation.models.beans.Document;

public class ContractsManager {
	
	private static final String Url = "http://127.0.0.1:8545";
	private static final int Conn_Attempts = 20;
	private static final int Conn_Timeout = 1000;
	private static final ContractGasProvider Gas_Provider = new StaticGasProvider(BigInteger.valueOf(100000), BigInteger.valueOf(3141592));
	private Web3j web3j;
	private Credentials cred;
	private TransactionManager txMgr;
	
	public ContractsManager(String password, File key) {
		try {
			this.web3j = Web3j.build(new HttpService(Url));
			log("Connected To Ethereum Network " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
			this.cred = WalletUtils.loadCredentials(password, key);
			log("Loaded Credentials ");
			this.txMgr = new RawTransactionManager(web3j, cred, Conn_Attempts, Conn_Timeout);
			log("Loaded Transaction Manager ");
			
		}
		
		catch (Exception e) {
			AppUtils.getError(e);
		}
		
	}
	
	public void setCertification(String contractAddress, Document document, String documentAddress, String serviceId, String publicKey) throws Exception{
		CertifyDocumentContract contract = loadCertifyDocument(contractAddress);
		contract.setCertification(document.getHash(), document.getUserSign(), publicKey, documentAddress, serviceId).send();
	}
	
	public CertificationFromContract getCertification(String contractAddress) throws Exception{
		CertifyDocumentContract contract = loadCertifyDocument(contractAddress);
		Tuple5<String, String, String, String, String> result = contract.getCertifications().send();
		CertificationFromContract cert = new CertificationFromContract();
		cert.DocumentHash = result.getValue1();
		cert.DigitalSign = result.getValue2();
		cert.PublicKey = result.getValue3();
		cert.DocumentLocation = result.getValue4();
		cert.ServiceId = result.getValue5();
		return cert;
		
	}
	
	public List<CertificationFromContract> getCertifications(List<String> contractsAddresses) throws Exception {
		List<CertificationFromContract> result = new ArrayList<CertificationFromContract>();
		for(String address : contractsAddresses) {
			result.add(getCertification(address));
		}
		return result;
	}
	
	public String deployCertifyDocument() throws Exception{
		CertifyDocumentContract contract = CertifyDocumentContract.deploy(web3j, txMgr, Gas_Provider).send();
		log("Deployed Contract At " + contract.getContractAddress());
		return contract.getContractAddress();
	}
	
	public CertifyDocumentContract loadCertifyDocument(String contractAddress) throws Exception {
		CertifyDocumentContract contract = CertifyDocumentContract.load(contractAddress, web3j, txMgr, Gas_Provider);
		log("Loaded Contract At " + contractAddress);
		return contract;
	}
	
	private static void log(String message) {
		AppUtils.log(message);
	}
}
