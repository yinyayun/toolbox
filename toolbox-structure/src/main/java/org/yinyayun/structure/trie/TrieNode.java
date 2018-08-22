package org.yinyayun.structure.trie;

import java.util.Arrays;

/**
 * 
 * @author yinyayun
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class TrieNode<T extends Comparable<T>> {
	// 该节点下所有的值数量，包含重复
	public int count = 0;
	public TrieEntry<T>[] ENTRYS = new TrieEntry[0];
	private TrieNode<T>[] CHILD_NODES = new TrieNode[0];
	private char[] CHARS = new char[0];

	/**
	 * 根据字符进行节点查找
	 * 
	 * @param c
	 * @return
	 */
	TrieNode<T> searchNode(char c) {
		return searchNode(CHARS, c);
	}

	private TrieNode<T> searchNode(char[] chars, char c) {
		int i = Arrays.binarySearch(chars, c);
		return i < 0 ? null : CHILD_NODES[i];
	}

	TrieNode<T> searchOrAdd(char c) {
		TrieNode<T> node = searchNode(c);
		if (node == null) {
			TrieNode<T> addNode = new TrieNode<T>();
			TrieNode<T>[] nodes = new TrieNode[CHILD_NODES.length + 1];
			char[] chars = new char[CHARS.length + 1];
			// 保证CHARS从小到大
			int i = 0;
			for (; i < CHARS.length; i++) {
				if (c < CHARS[i])
					break;
				chars[i] = CHARS[i];
				nodes[i] = CHILD_NODES[i];
			}
			chars[i] = c;
			nodes[i] = addNode;
			for (; i < CHARS.length; i++) {
				chars[i + 1] = CHARS[i];
				nodes[i + 1] = CHILD_NODES[i];
			}
			CHILD_NODES = nodes;
			CHARS = chars;
			return addNode;
		}
		return node;
	}

	private TrieEntry<T> searchEntry(T t) {
		int i = Arrays.binarySearch(ENTRYS, new TrieEntry<T>(t));
		return i < 0 ? null : ENTRYS[i];
	}

	void addEntry(T t) {
		TrieEntry<T> entry = searchEntry(t);
		if (entry == null) {
			entry = new TrieEntry<T>(t);
			TrieEntry<T>[] entrys = new TrieEntry[ENTRYS.length + 1];
			int i = 0;
			for (; i < ENTRYS.length; i++) {
				if (t.compareTo(ENTRYS[i].value) < 0)
					break;
				entrys[i] = ENTRYS[i];
			}
			entrys[i] = entry;
			for (; i < ENTRYS.length; i++) {
				entrys[i + 1] = ENTRYS[i];
			}
			ENTRYS = entrys;
		}
		entry.count++;
		count++;
	}

	@Override
	public String toString() {
		return "TrieNode [count=" + count + ", ENTRYS=" + Arrays.toString(ENTRYS) + "]";
	}
}