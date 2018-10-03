package qu.master.blockchain.gui.models;

public class LinkedNode<E> {
	
	private int order;
	private String key;
	private E data;
	private LinkedNode<E> prev;
	private LinkedNode<E> next;
	
	public LinkedNode() {
		
	}
	
	public LinkedNode cloneNode() {
		LinkedNode<E> clonedNode = new LinkedNode<E>();
		clonedNode.setData(this.data);
		
		if (this.prev != null) {
			clonedNode.setPrev(this.prev.cloneNode());
		}
		
		if (this.next != null) {
			clonedNode.setNext(this.next.cloneNode());
		}
		
		return clonedNode;
	}
	
	public LinkedNode(String key, E data, LinkedNode<E> prev, LinkedNode<E> next) {
		this.key = key;
		this.data = data;
		this.prev = prev;
		this.next = next;
	}
	
	private void resetOrder() {
		LinkedNode<E> previousNode = this.getPrev();
		int previousNodesCount = 0;
		
		while(previousNode != null) {
			previousNodesCount++;
			previousNode = previousNode.getPrev();
		}
		
		if (previousNodesCount > 0) {
			setOrder(previousNodesCount + 1);
		}
		
		else if (getNext() != null) {
			setOrder(1);
		}
		
		else {
			setOrder(0);
		}
		
		
	}
	
	public int getOrder() {
		
		if (this.order > 0) {
			return this.order;
		}
		
		else if (this.getPrev() != null) {
			return this.getPrev().getOrder() + 1;
		}
		
		else if (this.getNext() != null){
			return 1;
		}
		
		else {
			return 0;
		}
	}
	
	protected void setOrder(int order) {
		this.order = order;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public E getData() {
		return this.data;
	}
	
	public void setData(E data) {
		this.data = data;
	}
	
	public LinkedNode<E> getPrev() {
		return this.prev;
	}
	
	public void setPrev(LinkedNode<E> prev) {
		this.prev = prev;
		resetOrder();
	}
	
	public LinkedNode<E> getNext() {
		return this.next;
	}
	
	public void setNext(LinkedNode<E> next) {
		this.next = next;
		resetOrder();
	}
	
}
