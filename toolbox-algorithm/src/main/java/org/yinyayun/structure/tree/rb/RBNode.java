package org.yinyayun.structure.tree.rb;

import org.yinyayun.structure.bst.BSTNode;

/**
 * 红黑树节点
 * 
 * @author yinyayun
 *
 */
public class RBNode<T> extends BSTNode<T> {
	public boolean color;
	public RBNode<T> parent;

	public RBNode(int key, T data) {
		super(key, data);
	}

	@Override
	public String toString() {
		return "RBNode [color=" + color + ", key=" + key + ", data=" + data + "]";
	}

}
