
package org.yinyayun.structure.tree.bst;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.yinyayun.structure.tree.rb.RBNode;

/**
 * 二叉搜索树
 * 
 * @author yinyayun
 *
 * @param <T>
 */
public class BSTree<T> {
	public static void main(String[] args) {
		BSTree<Integer> tree = new BSTree<Integer>();
		int values[] = { 8, 6, 12, 5 };
		for (int value : values) {
			tree.insert(value, Integer.valueOf(value));
		}
		System.out.println(tree);
		System.out.println(tree.search(12));
		System.out.println(tree.search(6));
		System.out.println(tree.search(7));
		System.out.println("before remove 6");
		System.out.println(tree.nodeSize);
		System.out.println(tree.remove(6));
		System.out.println("after remove 6");
		System.out.println(tree.nodeSize);
		System.out.println(tree.search(12));
		System.out.println(tree.search(6));
		tree.printTree();
	}

	private BSTNode<T> ROOT;
	private int nodeSize;

	public BSTNode<T> insert(int key, T data) {
		if (ROOT == null) {
			ROOT = new BSTNode<T>(key, data);
		}
		BSTNode<T> temp = ROOT;
		return insert(temp, key, data);
	}

	protected BSTNode<T> insert(BSTNode<T> node, int key, T data) {
		if (node == null) {
			++nodeSize;
			return new BSTNode<T>(key, data);
		}
		if (key < node.key) {
			node.left = insert(node.left, key, data);
		} else if (key > node.key) {
			node.right = insert(node.right, key, data);
		}
		return node;

	}

	public BSTNode<T> remove(int key) {
		BSTNode<T> temp = ROOT;
		return remove(temp, key);
	}

	protected BSTNode<T> remove(BSTNode<T> node, int key) {
		if (node == null) {
			return null;
		}
		if (key < node.key) {
			node.left = remove(node.left, key);
		} else if (key > node.key) {
			node.right = remove(node.right, key);
		} else if (node.left != null && node.right != null) {
			BSTNode<T> minNode = findMinNode(node.right);
			node.key = minNode.key;
			node.data = minNode.data;
			node.right = remove(node.right, minNode.key);
		} else {
			//
			--nodeSize;
			return (node.left == null) ? node.right : node.left;
		}
		return node;
	}

	public BSTNode<T> search(int key) {
		BSTNode<T> temp = ROOT;
		return search(temp, key);
	}

	protected BSTNode<T> search(BSTNode<T> node, int key) {
		if (node == null) {
			return null;
		}
		if (key < node.key) {
			return search(node.left, key);
		} else if (key > node.key) {
			return search(node.right, key);
		} else {
			return node;
		}
	}

	public BSTNode<T> findMinNode(BSTNode<T> node) {
		BSTNode<T> temp = node;
		if (temp == null) {
			return null;
		}
		while (temp.left != null) {
			temp = temp.left;
		}
		return temp;
	}

	/**
	 * 父节点和叔叔节点为红色节点
	 * 
	 * @param unbalance
	 * @return
	 */
	public RBNode<T> flip(RBNode<T> unbalance) {
		return null;
	}

	/**
	 * 右子树上连续出现两个红节点<br>
	 * 
	 * @param node
	 * @return
	 */
	public BSTNode<T> leftRolation(BSTNode<T> unbalance) {
		BSTNode<T> node = unbalance.right;
		unbalance.right = node.left;
		node.left = unbalance;
		afterLeftRolation(unbalance, node);
		return node;
	}

	public void afterLeftRolation(BSTNode<T> unbalance, BSTNode<T> node) {
	}

	/**
	 * 左子树上连续出现两个红节点<br>
	 * 
	 * @param node
	 * @return
	 */
	public BSTNode<T> rightRolation(BSTNode<T> unbalance) {
		BSTNode<T> node = unbalance.left;
		unbalance.left = node.right;
		node.right = unbalance;
		afterRightRolation(unbalance, node);
		return node;
	}

	public void afterRightRolation(BSTNode<T> unbalance, BSTNode<T> node) {

	}

	/**
	 * 先左旋，再右旋
	 * 
	 * @param unbalance
	 * @return
	 */
	public BSTNode<T> lrRolation(BSTNode<T> unbalance) {
		BSTNode<T> node = leftRolation(unbalance.left);
		unbalance.left = node;
		return rightRolation(unbalance);
	}

	/**
	 * 先右旋，再左旋
	 * 
	 * @param unbalance
	 * @return
	 */
	public BSTNode<T> rlRolation(BSTNode<T> unbalance) {
		BSTNode<T> node = leftRolation(unbalance.right);
		unbalance.right = node;
		return rightRolation(unbalance);
	}

	public void printTree() {
		int n = (int) ((int) Math.log(nodeSize + 1) / Math.log(2));
		BSTNode<T> node = ROOT;
		List<BSTNode<T>> nodes = allNodes(node);
		for (int i = 0; i <= n; i++) {
			System.out.print(space(n - i));
			int number = (int) Math.pow(2, i);
			for (int j = 0; j < number; j++) {
				System.out.print(nodes.remove(0).key);
				System.out.print(space(2));
			}
			System.out.println();
		}
	}

	public List<BSTNode<T>> allNodes(BSTNode<T> node) {
		List<BSTNode<T>> nodes = new ArrayList<BSTNode<T>>();
		Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
		queue.add(node);
		BSTNode<T> header = null;
		while (!queue.isEmpty()) {
			header = queue.poll();
			nodes.add(header);
			if (header.left != null) {
				queue.offer(header.left);
			}
			if (header.right != null) {
				queue.offer(header.right);
			}
		}
		return nodes;
	}

	private String space(int size) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < size; i++) {
			builder.append("\t");
		}
		return builder.toString();
	}
}
