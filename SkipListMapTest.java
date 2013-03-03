package main;

import java.util.ArrayDeque;
import java.util.Queue;

import junit.framework.TestCase;

public class SkipListMapTest extends TestCase {
	private <K extends Comparable<K>> void AssertPut(SkipList<K, K> skipList, K keyAndValue) {
		assertEquals(keyAndValue, skipList.put(keyAndValue, keyAndValue));
		assertTrue(skipList.containsKey(keyAndValue));
	}

	private <K extends Comparable<K>> void AssertRemove(SkipList<K, K> skipList, K keyAndValue) {
		skipList.remove(keyAndValue);
		assertFalse(skipList.containsKey(keyAndValue));
	}

	private <K extends Comparable<K>> void AssertGet(SkipList<K, K> skipList, K keyAndValue) {
		assertTrue(skipList.containsKey(keyAndValue));
		assertEquals(keyAndValue, skipList.get(keyAndValue));
		assertTrue(skipList.containsKey(keyAndValue));
	}

	public void test() {
	
		Queue<Boolean> queueFalse = new ArrayDeque<Boolean>();
		Queue<Boolean> queueTrue = new ArrayDeque<Boolean>();
		
		for (int i=0; i<100; i++) {
			queueFalse.add(false);
			queueTrue.add(true);
		}
			
		SkipList<Integer, Integer> skipList = new SkipList<Integer, Integer>(new Random());
<<<<<<< HEAD
		SkipList<Integer, Integer> skipListFalse = new SkipList<Integer, Integer>(new Random(queueFalse));
		SkipList<Integer, Integer> skipListTrue = new SkipList<Integer, Integer>(new Random(queueTrue));
		SkipList<Integer, Integer> secondSkipList = new SkipList<Integer, Integer>(new Random());

		int[] ArrayOfKeyAndValue = new int[] {1, 0, -5, -7, 2, 1, -3, -100, 100, -5, 0, -101, 101, 50, -50, 44, 45, -25, 11, 49, 64, 33, 18, 0, 4, -2 };
		SkipList[] ArrayOfSkipList = new SkipList[] {skipList, skipListFalse, skipListTrue};
		
		for (int keyAndValue : ArrayOfKeyAndValue) 
			for (SkipList list : ArrayOfSkipList)
			AssertPut(list, keyAndValue);
		
		for (SkipList list : ArrayOfSkipList) 	
			assertFalse(list.isEmpty());

		for (int keyAndValue : ArrayOfKeyAndValue) 
			for (SkipList list : ArrayOfSkipList)
=======
		SkipList<Integer, Integer> skipListFalse = new SkipList<Integer, Integer>(new QueueRandom(queueFalse));
		SkipList<Integer, Integer> skipListTrue = new SkipList<Integer, Integer>(new QueueRandom(queueTrue));
		SkipList<Integer, Integer> secondSkipList = new SkipList<Integer, Integer>(new Random());

		int[] ArrayOfKeyAndValue = new int[] {1, 0, -5, -7, 2, 1, -3, -100, 100, -5, 0, -101, 101, 50, -50, 44, 45, -25, 11, 49, 64, 33, 18, 0, 4, -2 , 23 , 140, 152, 148, -132, -43, 54, 86, -23, 23, 44, 39, 111, -111};
		SkipList[] ArrayOfSkipList = new SkipList[] {skipList, skipListFalse, skipListTrue};
		
		for (int keyAndValue : ArrayOfKeyAndValue) 
			for (SkipList<Integer, Integer> list : ArrayOfSkipList)
			AssertPut(list, keyAndValue);
		for (SkipList<Integer, Integer> list : ArrayOfSkipList) 	
			assertFalse(list.isEmpty());

		for (int keyAndValue : ArrayOfKeyAndValue) 
			for (SkipList<Integer, Integer> list : ArrayOfSkipList)
>>>>>>> new
				AssertGet(list, keyAndValue);
		
		secondSkipList.putAll(skipList);
		secondSkipList.clear();
		assertTrue(secondSkipList.isEmpty());

		for (int keyAndValue : ArrayOfKeyAndValue)
<<<<<<< HEAD
			for (SkipList list : ArrayOfSkipList)
				AssertRemove(list, keyAndValue);
		
		for (SkipList list : ArrayOfSkipList) {	
=======
			for (SkipList<Integer, Integer> list : ArrayOfSkipList)
				AssertRemove(list, keyAndValue);
		
		for (SkipList<Integer, Integer> list : ArrayOfSkipList) {	
>>>>>>> new
			assertTrue(list.isEmpty());
			assertEquals(0, list.size());
		}
	}
}
