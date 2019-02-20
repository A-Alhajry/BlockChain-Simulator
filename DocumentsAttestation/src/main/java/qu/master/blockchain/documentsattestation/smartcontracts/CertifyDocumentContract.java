package qu.master.blockchain.documentsattestation.smartcontracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

public class CertifyDocumentContract extends Contract {
	
	private static final String Binary = "608060405234801561001057600080fd5b5033600560006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555061088c806100616000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680634598a87514610051578063a56fa6d1146101d2575b600080fd5b34801561005d57600080fd5b506101d0600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610412565b005b3480156101de57600080fd5b506101e761048c565b60405180806020018060200180602001806020018060200186810386528b818151815260200191508051906020019080838360005b8381101561023757808201518184015260208101905061021c565b50505050905090810190601f1680156102645780820380516001836020036101000a031916815260200191505b5086810385528a818151815260200191508051906020019080838360005b8381101561029d578082015181840152602081019050610282565b50505050905090810190601f1680156102ca5780820380516001836020036101000a031916815260200191505b50868103845289818151815260200191508051906020019080838360005b838110156103035780820151818401526020810190506102e8565b50505050905090810190601f1680156103305780820380516001836020036101000a031916815260200191505b50868103835288818151815260200191508051906020019080838360005b8381101561036957808201518184015260208101905061034e565b50505050905090810190601f1680156103965780820380516001836020036101000a031916815260200191505b50868103825287818151815260200191508051906020019080838360005b838110156103cf5780820151818401526020810190506103b4565b50505050905090810190601f1680156103fc5780820380516001836020036101000a031916815260200191505b509a505050505050505050505060405180910390f35b84600090805190602001906104289291906107bb565b50836001908051906020019061043f9291906107bb565b5082600290805190602001906104569291906107bb565b50816003908051906020019061046d9291906107bb565b5080600490805190602001906104849291906107bb565b505050505050565b606080606080606060006001600260036004848054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105335780601f1061050857610100808354040283529160200191610533565b820191906000526020600020905b81548152906001019060200180831161051657829003601f168201915b50505050509450838054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105cf5780601f106105a4576101008083540402835291602001916105cf565b820191906000526020600020905b8154815290600101906020018083116105b257829003601f168201915b50505050509350828054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561066b5780601f106106405761010080835404028352916020019161066b565b820191906000526020600020905b81548152906001019060200180831161064e57829003601f168201915b50505050509250818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107075780601f106106dc57610100808354040283529160200191610707565b820191906000526020600020905b8154815290600101906020018083116106ea57829003601f168201915b50505050509150808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107a35780601f10610778576101008083540402835291602001916107a3565b820191906000526020600020905b81548152906001019060200180831161078657829003601f168201915b50505050509050945094509450945094509091929394565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106107fc57805160ff191683800117855561082a565b8280016001018555821561082a579182015b8281111561082957825182559160200191906001019061080e565b5b509050610837919061083b565b5090565b61085d91905b80821115610859576000816000905550600101610841565b5090565b905600a165627a7a72305820702f79124fb0a38e0ebd440bf13a7cee122f07c068b9a6658c274aa4c1e91da70029";
	
	public static final String FUNC_SETCERTIFICATION = "setCertification";
	
	public static final String FUNC_GETCERTIFICATION = "getCertification";
	
	protected CertifyDocumentContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider gasProvider) {
		super(Binary, contractAddress, web3j, transactionManager, gasProvider);
	}
	
	protected CertifyDocumentContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
		super(Binary, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
	}
	
	public RemoteCall<TransactionReceipt> setCertification(String docHash, String digiSign, String pubKey, String docLoc, String srvId) {
		Utf8String _docHash = new Utf8String(docHash);
		Utf8String _digiSign = new Utf8String(digiSign);
		Utf8String _pubKey = new Utf8String(pubKey);
		Utf8String _docLoc = new Utf8String(docLoc);
		Utf8String _srvId = new Utf8String(srvId);
		
		final Function function = new Function(FUNC_SETCERTIFICATION, Arrays.<Type>asList(_docHash, _digiSign, _pubKey, _docLoc, _srvId), Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}
	
	public RemoteCall<Tuple5<String, String, String, String, String>> getCertifications() {
		
		List<TypeReference<?>> outputList = new ArrayList<>();
		outputList.add(new TypeReference<Utf8String>() {});
		outputList.add(new TypeReference<Utf8String>() {});
		outputList.add(new TypeReference<Utf8String>() {});
		outputList.add(new TypeReference<Utf8String>() {});
		outputList.add(new TypeReference<Utf8String>() {});
		
		final Function function = new Function(FUNC_GETCERTIFICATION, Arrays.<Type>asList(), outputList);
		
		return new RemoteCall<Tuple5<String, String, String, String, String>>(
				 new Callable<Tuple5<String, String, String, String, String>>() {
					@Override
					public Tuple5<String, String, String, String, String> call() throws Exception{
						List<Type> results = executeCallMultipleValueReturn(function);
						return new Tuple5<String, String, String, String, String>(
									(String) results.get(0).getValue(),
									(String) results.get(1).getValue(),
									(String) results.get(2).getValue(),
									(String) results.get(3).getValue(),
									(String) results.get(4).getValue()
								);
					}
				}
			);
	}
	
	@SuppressWarnings("deprecation")
	public static RemoteCall<CertifyDocumentContract> deploy(Web3j web3j, TransactionManager txMgr, ContractGasProvider gasProvider) {
		return deployRemoteCall(CertifyDocumentContract.class, web3j, txMgr, gasProvider.getGasPrice(), gasProvider.getGasLimit(), Binary, "");
	}
	
	public static CertifyDocumentContract load(String contractAddress, Web3j web3j, TransactionManager txMgr, ContractGasProvider gasProvider) {
		return new CertifyDocumentContract(contractAddress, web3j, txMgr, gasProvider);
	}
}