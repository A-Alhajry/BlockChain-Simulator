package qu.master.blockchain.gui.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LinkedList<E> {
	
	private LinkedNode<E> head;
	private LinkedNode<E> tail;
	private static final int initialCapacity = 100;
	private Object[] nodes;
	private int currentIndex = -1;
	
	public LinkedList() {
		nodes = new Object[initialCapacity];
		head = new LinkedNode<E>();
		tail = new LinkedNode<E>();
	}
	
	protected LinkedList(LinkedNode<E> head, LinkedNode<E> tail) {
		nodes = new Object[initialCapacity];
		this.head = head;
		this.tail = tail;
	}
	
	private LinkedNode<E>[] getEmptyArray(int size) {
		Object[] newArray = new Object[size];
		
		for(int i = 0 ; i < size; i++) {
			newArray[i] = new LinkedNode<E>();
		}
		
		return (LinkedNode<E>[]) newArray;
	}
	
	public LinkedNode<E> getHead() {
		return (LinkedNode<E>) this.head;
	}
	
	public LinkedNode<E> getTail() {
		return this.tail;
	}
	
	public void appendNode(LinkedNode<E> newNode) {
		head.setPrev(newNode);
		currentIndex++;
		
		if (currentIndex == nodes.length) {
			throw new IllegalArgumentException("LinkedList Is Full. Maximum Allowed Size is 100");
		}
		
		nodes[currentIndex] = newNode;
	}
	
	public void preappendNode(LinkedNode<E> newNode) {
		tail.setNext(newNode);
		currentIndex++;
		
		if (currentIndex == nodes.length) {
			throw new IllegalArgumentException("LinkedList Is Full. Maximum Allowed Size is 100");
		}
		
		for(int i = 1; i < nodes.length; i++) {
			nodes[i] = nodes[i - 1];
		}
		nodes[0] = newNode;
	}
	
	public LinkedNode<E> removeTop() {
		LinkedNode<E> topNode = this.head.getPrev();
		
		if (currentIndex < 0) {
			throw new IllegalArgumentException("LinkedList Is Empty ");
		}
		
		nodes[currentIndex] = null;
		currentIndex--;
		return topNode;
	}
	
	public LinkedNode<E> removeBottom() {
		
		if (currentIndex < 0) {
			throw new IllegalArgumentException("LinkedList Is Empty");
		}
		
		LinkedNode<E> bottom = this.tail.getNext();
		currentIndex--;
		for(int i = 0; i < nodes.length - 1; i++) {
			nodes[i] = nodes[i - 1];
		}
		return bottom;
	}
	
	public LinkedNode<E> getNodeByIndex(int index) {
		try {
			return (LinkedNode<E>) this.nodes[index];
		}
		
		catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public LinkedNode<E> getNodeByKey(String key) {
		for(int i = 0; i < currentIndex; i++) {
			if (getNodeByIndex(i).getKey().equals(key)) {
				return getNodeByIndex(i);
			}
		}
		
		return null;
	}
	
	public List<LinkedNode<E>> getNodes() {
		List<LinkedNode<E>> nodes = new ArrayList<>();
		
		for(int i = 0; i <= currentIndex; i++) {
			LinkedNode<E> node = (LinkedNode<E>) this.nodes[i];
			nodes.add(node);
		}
		
		return nodes;
	}
}
