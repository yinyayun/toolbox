package org.yinyayun.structure.tree.avl;

/**
 * 二叉平衡树节点
 * 
 * @author yinyayun
 *
 */
public class AVLNode<T> {
	public AVLNode<T> left;
	public AVLNode<T> right;
	public int key;
	public T data;
	public int height;// 表示从当前节点到叶子节点路径上的节点数

	public AVLNode(int key, T data) {
		super();
		this.key = key;
		this.data = data;
	}

	@Override
	public String toString() {
		return "[key:" + key + "," + "height:" + height + "]";
	}
}
