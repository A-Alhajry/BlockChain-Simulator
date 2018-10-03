package qu.master.blockchain.gui.models;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TreeBlockChain<T> extends BinaryTree<T> implements BlockChain<T> {
	
	private List<Consumer<Boolean>> validationObservers = new ArrayList<>();
	
	public boolean validateMerkleTree() {
		
		int lastNodeOrder = super.getLastNodeOrder();
		boolean isValid = true;
		
		for(int i = 0; i < lastNodeOrder; i++) {
			TreeBlock<T> block = this.getBlockByIndex(i);
			isValid = isValid && block.validateBlock();
		}
		
		callValidationObservers(isValid);
		return isValid;
	}
	
	
	
	public void addValidationObserver(Consumer<Boolean> observer) {
		validationObservers.add(observer);
	}
	
	public boolean validateChain() {
		return validateMerkleTree();
	}
	 
	private void callValidationObservers(boolean isValid) {
		for(Consumer<Boolean> observer : validationObservers) {
			observer.accept(isValid);
		}
	}
	
	public TreeBlock<T> getBlockByIndex(int index) {
		return (TreeBlock<T>) super.getNodeByOrder(index + 1);
	}
	
	public static TreeBlockChain<String> getSampleTree() {
//		TreeBlock<String> block32 = new TreeBlock<String>("", "00002ee7ac1c4ecdcac019f122e4927652abf2f62e1f948ed12d23fb8a3167ea", 32, null, null, null, null);
//		TreeBlock<String> block31 = new TreeBlock<String>("", "00002ee7ac1c4ecdcac019f122e4927652abf2f62e1f948ed12d23fb8a3167ea", 31, null, null, null, null);
//		TreeBlock<String> block30 = new TreeBlock<String>("", "00002ee7ac1c4ecdcac019f122e4927652abf2f62e1f948ed12d23fb8a3167ea", 30, null, null, null, null);
//		TreeBlock<String> block29 = new TreeBlock<String>("", "00002ee7ac1c4ecdcac019f122e4927652abf2f62e1f948ed12d23fb8a3167ea", 29, null, null, null, null);
//		TreeBlock<String> block28 = new TreeBlock<String>("", "00002ee7ac1c4ecdcac019f122e4927652abf2f62e1f948ed12d23fb8a3167ea", 28, null, null, null, null);
//		TreeBlock<String> block27 = new TreeBlock<String>("", "00002ee7ac1c4ecdcac019f122e4927652abf2f62e1f948ed12d23fb8a3167ea", 27, null, null, null, null);
//		TreeBlock<String> block26 = new TreeBlock<String>("", "00002ee7ac1c4ecdcac019f122e4927652abf2f62e1f948ed12d23fb8a3167ea", 26, null, null, null, null);
//		TreeBlock<String> block25 = new TreeBlock<String>("", "00002ee7ac1c4ecdcac019f122e4927652abf2f62e1f948ed12d23fb8a3167ea", 25, null, null, null, null);
//		TreeBlock<String> block24 = new TreeBlock<String>("", "00002ee7ac1c4ecdcac019f122e4927652abf2f62e1f948ed12d23fb8a3167ea", 24, null, null, null, null);
//		TreeBlock<String> block23 = new TreeBlock<String>("", "00002ee7ac1c4ecdcac019f122e4927652abf2f62e1f948ed12d23fb8a3167ea", 23, null, null, null, null);
//		TreeBlock<String> block22 = new TreeBlock<String>("", "00002ee7ac1c4ecdcac019f122e4927652abf2f62e1f948ed12d23fb8a3167ea", 22, null, null, null, null);
//		TreeBlock<String> block21 = new TreeBlock<String>("", "00002ee7ac1c4ecdcac019f122e4927652abf2f62e1f948ed12d23fb8a3167ea", 21, null, null, null, null);
//		TreeBlock<String> block20 = new TreeBlock<String>("", "00002ee7ac1c4ecdcac019f122e4927652abf2f62e1f948ed12d23fb8a3167ea", 20, null, null, null, null);
//		TreeBlock<String> block19 = new TreeBlock<String>("", "00002ee7ac1c4ecdcac019f122e4927652abf2f62e1f948ed12d23fb8a3167ea", 19, null, null, null, null);
//		TreeBlock<String> block18 = new TreeBlock<String>("", "00002ee7ac1c4ecdcac019f122e4927652abf2f62e1f948ed12d23fb8a3167ea", 18, null, null, null, null);
//		TreeBlock<String> block17 = new TreeBlock<String>("", "00002ee7ac1c4ecdcac019f122e4927652abf2f62e1f948ed12d23fb8a3167ea", 17, null, null, null, null);
		//TreeBlock<String> block16 = new TreeBlock<String>("", "637519","0000965e3e7fc300989c81f6e50c9d224d09f93434925af38d2f2f78e5aabc0b", 16, 5, null, null, null, null);
		TreeBlock<String> block15 = new TreeBlock<String>("", "341920", "000034af06508da7bdd0db8e80e24d18b0410dff0387276cb728d680b67bc262", 15, 4, null, null, null, null);
		TreeBlock<String> block14 = new TreeBlock<String>("", "595351", "0000daacb24c4232c6626e4aa16cf6ff538fadac2dc32216f8b73a7c8712a4a4", 14, 4, null, null, null, null);
		TreeBlock<String> block13 = new TreeBlock<String>("", "69511", "0000d3ff6709400fed607281b763c73cdc3468b0b4cb0b1df876f0c605a9ad13", 13, 4, null, null, null, null);
		TreeBlock<String> block12 = new TreeBlock<String>("", "506136", "0000fd6fdd810ca331c76a36284dd5c8b630d3da557696373b0a204c0ff112e8", 12, 4, null, null, null, null);
		TreeBlock<String> block11 = new TreeBlock<String>("", "9371", "0000e6b89b0f08f0f7faf26f0ce60185e5043f11ed84c8881033ea0580011111", 11, 4, null, null, null, null);
		TreeBlock<String> block10 = new TreeBlock<String>("", "893510", "00004f9d40ea4367cdf9da95771bd58460309dce858115c418591b9d3c3523c7", 10, 4, null, null, null, null);
		TreeBlock<String> block9 = new TreeBlock<String>("", "226064", "0000d737e6ea2b52a03c274b95e42ac52955c8a98e7484679cc772947ba09ab1", 9, 4, null, null, null, null);
		TreeBlock<String> block8 = new TreeBlock<String>("", "985248", "000077b1bed695c51f724ff733372c6875132aef959211194014add2a135a7b4", 8, 4, null, null, null, null);
		TreeBlock<String> block7 = new TreeBlock<String>("", "680100", "0000c3c2a80138712c5d303b2193f8d2b342143a2872cf8469f43b2c88a26b64", 7, 3, block15, block14, block15.getHash(), block14.getHash());
		TreeBlock<String> block6 = new TreeBlock<String>("", "394008", "000086f28a59cad1674365053f317d23d71a674221f680ead330fe5ca9ed1865", 6, 3, block13, block12, block13.getHash(), block12.getHash());
		TreeBlock<String> block5 = new TreeBlock<String>("", "965902", "0000458fa85c31612376ab34fce818a5d3b1f95e71cd5e1aef91db8d1106426f", 5, 3, block11, block10, block11.getHash(), block10.getHash());
		TreeBlock<String> block4 = new TreeBlock<String>("", "515336", "00006c66d15f7c9dfed27eabfa1775dc1cf0e141bc2ba2d7d7982df38949504f", 4, 3, block9, block8, block9.getHash(), block8.getHash());
		TreeBlock<String> block3 = new TreeBlock<String>("", "864498", "0000aac9577ea9f553eb1da83f03938237d14d57d7c4022b5d06963b7421f6b1", 3, 2, block7, block6, block7.getHash(), block6.getHash());
		TreeBlock<String> block2 = new TreeBlock<String>("", "478977", "00003304e2a3dad55a69fd66298f8c0114c8879ef85b9b9bce524aa8a1500b14", 2, 2, block5, block4, block5.getHash(), block4.getHash());
		TreeBlock<String> block1 = new TreeBlock<String>("", "462531", "00005dccf31578c9bd03a05380835c44bca060f01c812c8be3d720a8711c46f2", 1, 1, block3, block2, block3.getHash(), block2.getHash());
		
		TreeBlockChain<String> chain = new TreeBlockChain<>();
		chain.appendNode(block1);
		chain.appendNode(block2);
		chain.appendNode(block3);
		chain.appendNode(block4);
		chain.appendNode(block5);
		chain.appendNode(block6);
		chain.appendNode(block7);
		chain.appendNode(block8);
		chain.appendNode(block9);
		chain.appendNode(block10);
		chain.appendNode(block11);
		chain.appendNode(block12);
		chain.appendNode(block13);
		chain.appendNode(block14);
		chain.appendNode(block15);
		//chain.appendNode(block16);
		
		return chain;
	}

	
}
