package qu.master.blockchain.gui.models;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree<T> {
	
	private Object[] nodes = new Object[100];
	private int currentIndex = -1;
	private int currentLevel = 1;
	
	public void appendNode(TreeNode<T> node) {
		nodes[++currentIndex] = node;
		setNodeLevel(node);
	}
	
	public int appendRootNode(TreeNode<T> node) {
		nodes[0] = node;
		currentIndex++;
		currentLevel = 2;
		return 1;
	}
	public int appendRightNode(int order, TreeNode<T> node) {
		nodes[(order * 2) - 1] = node;
		return (order * 2) - 1;
	}
	
	public int appendLeftNode(int order, TreeNode<T> node) {
		nodes[order * 2] = node;
		return order * 2;
	}
	
	public TreeNode<T> getNodeByOrder(int order) {
		return (TreeNode<T>) nodes[order - 1];
	}
	
	public TreeNode<T> getRightNode(int order) {
		return (TreeNode<T>) nodes[(order * 2) - 1];
	}
	
	public TreeNode<T> getLeftNode(int order) {
		return (TreeNode<T>) nodes[order * 2 ];
	}
	
	public int getLastNodeOrder() {
		for(int i = 0; i < nodes.length; i++) {
			if (nodes[i] == null) {
				return i;
			}
		}
		
		return 0;
		
		//return currentIndex + 1;
	}
	
	public List<TreeNode<T>> getAllNodes() {
		int lastNodeIndex = -1;
		List<TreeNode<T>> nodes = new ArrayList<>();
		
		for(int i = 0; i < this.nodes.length; i++) {
			if (this.nodes[i] == null) {
				lastNodeIndex = i;
				break;
			}
		}
		
		return nodes.subList(0, lastNodeIndex);
	}
	
	private void setNodeLevel(TreeNode<T> node) {
		//node.setLevel(currentLevel);
		
		if ((Math.pow(2, currentLevel) - 1) == getLastNodeOrder() - 1) {
			currentLevel++;
		}
		
		
	}
}
