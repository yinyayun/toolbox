package org.yinyayun.structure.trie;

/**
 * 词典树实体
 * 
 * @author yinyayun
 *
 * @param <T>
 */
public class TrieEntry<T extends Comparable<T>> implements Comparable<TrieEntry<T>> {
	public T value;
	public int count = 0;

	public TrieEntry(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Entry [value=" + value + ", count=" + count + "]";
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TrieEntry) {
			return value.equals(((TrieEntry<?>) obj).value);
		}
		return false;
	}

	@Override
	public int compareTo(TrieEntry<T> o) {
		return value.compareTo(o.value);
	}
}