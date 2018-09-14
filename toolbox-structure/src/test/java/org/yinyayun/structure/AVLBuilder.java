package org.yinyayun.structure;

import org.yinyayun.structure.tree.avl.AVLNode;

public class AVLBuilder {
	public void print(AVLNode<Integer> root) {
		
	}

	/**
	 * 先序遍历
	 * 
	 * @param root
	 */
	public void it1(AVLNode<Integer> root) {
		if (root == null)
			return;
		System.out.println(root);
		it1(root.left);
		it1(root.right);
	}

	public boolean isBlance(AVLNode<Integer> root) {
		int factor = 2;
		if (root != null) {
			factor = root.left.height - root.right.height;
		}
		return Math.abs(factor) < 2;
	}

	public void leafRotation(AVLNode<Integer> x) {
		AVLNode<Integer> y = x.right;
		x.right = y.left;
		y.left = x;
		x.height = Math.max(x.left.height, x.right.height);
		y.height = Math.max(y.left.height, y.right.height);
	}

	public AVLNode<Integer> search(AVLNode<Integer> node, int e) {
		if (node == null) {
			return null;
		}
		if (e == node.data) {
			return node;
		} else if (e > node.data) {
			return search(node.right, e);
		} else {
			return search(node.left, e);
		}
	}
}
