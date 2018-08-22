package org.yinyayun.structure.trie;

/**
 * 字典Map，支持多value存储 （1）内存压缩，存储主要使用数组 （2）统计，包含重复value的统计，key值对应value的统计
 * 
 * @author yinyayun
 *
 * @param <T>
 */
public class TrieMap<T extends Comparable<T>> {
	private TrieNode<T> ROOT = new TrieNode<T>();
	private int size;

	public void put(String key, T t) {
		TrieNode<T> node = ROOT;
		for (int i = 0; i < key.length(); ++i)
			node = node.searchOrAdd(key.charAt(i));
		node.addEntry(t);
		size++;
	}

	public TrieNode<T> get(char c, TrieNode<T> fromNode) {
		TrieNode<T> node = fromNode.searchNode(c);
		return node;
	}

	public TrieNode<T> get(String key) {
		TrieNode<T> node = ROOT;
		for (int i = 0; i < key.length(); ++i) {
			node = node.searchNode(key.charAt(i));
			if (node == null) {
				return null;
			}
		}
		return node;
	}

	public TrieNode<T> getOrDefault(String key, TrieNode<T> def) {
		TrieNode<T> node = get(key);
		return node == null ? def : node;
	}

	public TrieNode<T> getRoot() {
		return ROOT;
	}

	public int size() {
		return size;
	}

}
