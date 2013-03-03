package main;
public class Node<K extends Comparable<K>, V> implements Comparable<K> {
	protected K key;
	protected V value;
	protected Node<K, V> next;
	protected Node<K, V> down;
	
	public Node(){
		key = null;
		value = null;
		next = null;
		down = null;
	}


	public Node(K key, V value, Node<K, V> next, Node<K, V> down){
		this.key = key;
		this.value = value;
		this.next = next;
		this.down = down;
	}

	public int compareTo(K o) {
		return o.compareTo(this.key);
	}
		
}
