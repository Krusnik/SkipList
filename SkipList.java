package main;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class SkipList<K extends Comparable<K>, V> implements Map<K, V> {
	private int countLine = 0;
	private int size = 0;
	private Randomaze random;
	private Node<K, V> predict;
	private Node<K, V> first;
	private Node<K, V>[] listOfPredict = new Node[32];

	SkipList(Randomaze randomaze) {
		this.random = randomaze;
	}

	private void addLine() {
		Node<K, V> tempFirst = new Node<K, V>();
		Node<K, V> tempLast = new Node<K, V>();
		tempFirst = new Node<K, V>(first.key, first.value, first.next,
				first.down);
		tempLast = new Node<K, V>(first.next.next.key, first.next.next.value,
				first.next.next.next, first.next.next.down);
		tempFirst.down = first;
		tempLast.down = first.next.next;
		tempFirst.next = tempLast;
		first = tempFirst;
	}

	private Node<K, V> search(Object key) {
		Node<K, V> tempFirst = first;
		countLine = 0;
		if (tempFirst == null) {
			predict = tempFirst;
			return null;
		}
		do {
			predict = tempFirst;
			while (tempFirst != null) {
				if (tempFirst.key.compareTo((K) key) == 0)
					return tempFirst;
				if (tempFirst.key.compareTo((K) key) > 0) {
					if (predict.down == null)
						return predict;
					if (countLine < 32)
						listOfPredict[countLine++] = predict;
					predict = predict.down;
					tempFirst = predict;
					break;
				}
				if (predict != tempFirst)
					predict = predict.next;
				tempFirst = tempFirst.next;
			}
		} while (tempFirst != null);
		return predict;
	}

	public void clear() {
		first = null;
		size = 0;
	}

	public boolean containsKey(Object key) {
		if (first != null) {
			return search(key).key.equals(key);
		}
		return false;
	}

	public boolean containsValue(Object arg0) {
		Node<K, V> tempFirst = first;
		if (first != null) {
			while (tempFirst.down != null)
				tempFirst = tempFirst.down;
			while (tempFirst != null) {
				if (tempFirst.value.equals(arg0))
					return true;
				tempFirst = tempFirst.next;
			}
		}
		return false;
	}

	public V get(Object key) {
		Node<K, V> result = search(key);
		if (result != null && result.key.equals(key))
			return (V) result.value;
		return null;
	}

	public boolean isEmpty() {
		return (first == null);
	}

	public LinkedHashSet<K> keySet() {
		LinkedHashSet<K> keys = new LinkedHashSet<K>();
		Node<K, V> tempFirst = first;
		while (tempFirst != null && tempFirst.down != null) {
			tempFirst = tempFirst.down;
		}
		while (tempFirst != null) {
			keys.add((K) tempFirst.key);
			tempFirst = tempFirst.next;
		}
		return keys;
	}

	private V replaceExistElement(Node<K, V> result, V value) {
		while (result != null) {
			result.value = value;
			result = result.down;
		}
		return value;
	}

	private V addInEmptyList(Node<K, V> current, V value) {
		size++;
		first = current;
		return value;
	}

	private void addFirstElement(Node<K, V> result, Node<K, V> current){
		result=getPreviousElementAtFirstLevel(result.key, result);
		current.next=result;
		for (int i=countLine - 2; i>=0; i--) {
			Node<K, V> newSkipList = new Node<K, V> (current.key, current.value, listOfPredict[i], current);
			current=newSkipList;
		}
		first=current;
	}

	private void addLastElement(Node<K, V> result, Node<K, V> current){
		result=getPreviousElementAtFirstLevel(result.key,result);
		result.next=current;
		for (int i=countLine - 2; i>=0; i--) {
			Node<K, V> newSkipList= new Node<K, V>(current.key, current.value, null, listOfPredict[i+1].next);
			listOfPredict[i].next=newSkipList;
		}
	}

	private void addMiddleElement(Node<K, V> result, Node<K, V> current){
		current.next = result.next;
		result.next = current;
		listOfPredict[countLine]=result;
		for (int i=countLine - 1; i>=0 && random.newLevel(); i--) {
			Node<K, V> newSkipList = new Node<K, V>(current.key, current.value, listOfPredict[i].next, listOfPredict[i+1].next);
			listOfPredict[i].next = newSkipList;
		}
	}

	public V put(K key, V value) {
		Node<K, V> result = search(key);
		Node<K, V> current = new Node<K, V>(key, value, null, null);
		Node<K, V> tempFirst = first;

		if (result == null)
			return addInEmptyList(current, value);

		if (result.key == key) {
			return replaceExistElement(result, value);			
		}
		if (result.key.compareTo(key) > 0) 
			addFirstElement(result, current);
		else if (result.next == null) 
			addLastElement(result, current);		
		else 
			addMiddleElement(result, current);

		if (first.next.next != null)
			addLine();
		size++;
		return value;
	}
	
	public int size() {
		return size;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (K key : m.keySet())
			this.put(key, m.get(key));
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	private void removeFirstElement(Object key, Node<K, V> result) {
		countLine = 0;
		result = getPreviousElementAtFirstLevel(first.key, result);
		result = result.next;
		for (int i = countLine - 2; i >= 0; i--)
			if (result != null && !listOfPredict[i].next.key.equals(result.key)) {
				Node<K, V> temp = new Node<K, V>(result.key, result.value,
						result.next, result.down);
				temp.next = listOfPredict[i].next;
				temp.down = listOfPredict[i + 1].next;
				listOfPredict[i].next = temp;
			}
		first = first.next;
	}

	private void removeLastElement(Object key) {
		predict = getPreviousElementAtFirstLevel(key, predict);
		predict.next = null;
		for (int i = countLine - 1; i >= 0; i--) {
			if (predict.key == listOfPredict[i].key)
				listOfPredict[i].next = null;
			else
				listOfPredict[i].next = predict;
		}
	}

	private Node<K, V> getPreviousElementAtFirstLevel(Object key,
			Node<K, V> skipList) {
		while (skipList != null) {
			listOfPredict[countLine++] = skipList;
			skipList = skipList.down;
			skipList = getPreviousElementAtLevel(key, skipList);
		}
		return listOfPredict[countLine - 1];
	}

	private Node<K, V> getPreviousElementAtLevel(Object key, Node<K, V> skipList) {
		while (skipList != null && !skipList.key.equals(key)
				&& !skipList.next.key.equals(key))
			skipList = skipList.next;
		return skipList;

	}

	private void removeMiddleElement(Object key, Node<K, V> result) {
		Node<K, V> temp = listOfPredict[countLine - 1];
		temp = temp.down;
		while (temp != null) {
			temp = getPreviousElementAtLevel(result.key, temp);
			temp.next = temp.next.next;
			temp = temp.down;
		}
	}

	@Override
	public V remove(Object key) {
		Node<K, V> result = search(key);
		if (result == null || !result.key.equals(key))
			return null;
		size--;

		V out = result.value;
		if (size == 0) {
			first = null;
			return out;
		}
		if (first == result) {
			removeFirstElement(key, result);
			return out;
		}
		if (result.next == null) {
			removeLastElement(key);
			return out;
		}
		removeMiddleElement(key, result);
		return out;
	}
}
