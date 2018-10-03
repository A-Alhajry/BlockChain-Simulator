package qu.master.blockchain.gui.models;

public class TreeNode<E> {
	
	private E data;
	private String key;
	private TreeNode<E> right;
	private TreeNode<E> left;
	private int level;
	
	public TreeNode() {
		
	}
	
	public TreeNode(E data, TreeNode<E> right, TreeNode<E> left) {
		this.data = data;
		this.right = right;
		this.left = left;
	}

	public E getData() {
		return this.data;
	}
	
	public void setData(E data) {
		this.data = data;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public TreeNode<E> getRight() {
		return this.right;
	}
	
	public void setRight(TreeNode<E> right) {
		this.right = right;
	}
	
	public TreeNode<E> getLeft() {
		return this.left;
	}
	
	public void setLeft(TreeNode<E> left) {
		this.left = left;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
}
