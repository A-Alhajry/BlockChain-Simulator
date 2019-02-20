package qu.master.blockchain.documentsattestation.smartcontracts;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

public abstract class AbstractContract extends Contract {
	
	private String binary;
	private Class<? extends AbstractContract> klass;
	
	protected AbstractContract(Class<? extends AbstractContract> klass, String binary, String contractAddress, Web3j web3j, TransactionManager txMgr, ContractGasProvider gasProvider) {
		super(binary, contractAddress, web3j, txMgr, gasProvider);
		this.binary = binary;
		this.klass = klass;
	}
	
	public RemoteCall<? extends AbstractContract> deployContract(Web3j web3j, TransactionManager txMgr, ContractGasProvider gasProvider) {
		return deployRemoteCall(this.getClass(), web3j, txMgr, gasProvider.getGasPrice(), gasProvider.getGasLimit(), this.binary, "");
	}
	
	public AbstractContract loadContract(String contractAddress, Web3j web3j, TransactionManager txMgr, ContractGasProvider gasProvider) {
		return this;
	}
	
	
	
	
}
