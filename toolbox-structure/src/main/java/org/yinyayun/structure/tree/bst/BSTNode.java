package org.yinyayun.structure.tree.bst;

public class BSTNode<T> {
	public int key;
	public T data;
	public BSTNode<T> left;
	public BSTNode<T> right;

	public BSTNode(int key, T data) {
		this.key = key;
		this.data = data;
	}

	@Override
	public String toString() {
		return "BSTNode [key=" + key + ", data=" + data + "]";
	}

}
