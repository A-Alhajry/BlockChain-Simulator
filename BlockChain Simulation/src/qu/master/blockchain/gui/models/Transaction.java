package qu.master.blockchain.gui.models;

import java.util.*;

public class Transaction {
	
	private String txHash;
	private List<TransactionInput> listOfInputs;
	private List<TransactionOutput> listOfOutputs;
	
	public Transaction() {
		this.listOfInputs = new ArrayList<>();
		this.listOfOutputs = new ArrayList<>();
	}
	
	public String getTxHash() {
		return txHash;
	}
	
	public String getBody() {
		StringBuilder body = new StringBuilder();
		
		for(TransactionInput input : listOfInputs) {
			body.append(input.getPrevTxHash() + input.getOutputIndex());
		}
		
		for(TransactionOutput output : listOfOutputs) {
			body.append(output.getValue() + output.getPublicKey());
		}
		
		return body.toString();
	}
	
	private void updateTxHash() {
		this.txHash = HashingModel.getSha256Hash(getBody());
	}
	
	public void addInput(TransactionInput input) {
		listOfInputs.add(input);
		updateTxHash();
	}
	
	public void addOutput(TransactionOutput output) {
		listOfOutputs.add(output);
		updateTxHash();
	}
	
	@Override
	public String toString() {
		
		return super.toString();
	}
	
	public List<TransactionInput> getListOfInputs() {
		return this.listOfInputs;
	}
	
	public List<TransactionOutput> getListOfOutputs() {
		return this.listOfOutputs;
	}
}
