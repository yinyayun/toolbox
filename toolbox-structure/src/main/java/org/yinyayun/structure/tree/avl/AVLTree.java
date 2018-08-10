package org.yinyayun.structure.tree.avl;

/**
 * 平衡二叉搜索树,任何一个节点大于左子节点，小于右子节点
 * 
 * @author yinyayun
 */
public class AVLTree<T> {
	private AVLNode<T> ROOT;

	public void insert(int key, T value) {
		if (ROOT == null) {
			ROOT = insertNode(null, key, value);
		} else {
			insertNode(ROOT, key, value);
		}
	}

	/**
	 * 插入节点
	 * 
	 * @param node
	 * @param key
	 * @param t
	 * @return
	 */
	public AVLNode<T> insertNode(AVLNode<T> node, int key, T t) {
		if (node == null) {
			return new AVLNode<T>(key, t);
		}
		if (key < node.key) {
			// 小于当前节点，向左插入
			node.left = insertNode(node.left, key, t);
			rebalance(node, key);
		} else if (key > node.key) {
			node.right = insertNode(node.right, key, t);
			rebalance(node, key);
		} else {
			node.data = t;
		}
		// 已经存在，实际上不需要进行高度+1
		node.height = Math.max(height(node.left), height(node.right)) + 1;
		return node;
	}

	public AVLNode<T> search(int key) {
		return search(ROOT, key);
	}

	public AVLNode<T> search(AVLNode<T> node, int key) {
		if (node == null)
			return null;
		if (key < node.key) {
			return search(node.left, key);
		} else if (key > node.key) {
			return search(node.right, key);
		} else {
			return node;
		}
	}

	public AVLNode<T> delete(AVLNode<T> node, int key) {
		if (node == null)
			return null;
		if (key < node.key) {
			return delete(node.left, key);
		} else if (key > node.key) {
			return delete(node.right, key);
		}
		// 叶子节点，直接删除
		if (node.left == null && node.left == null) {
			node = null;
		} else if (node.left == null && node.right != null) {
			// 有右子树
			node = node.right;
		} else if (node.left != null && node.right == null) {
			// 有左子树
			node = node.left;
		} else {
			// 存在两个子树
			AVLNode<T> minNode = findMinNode(node.right);
			node.key = minNode.key;
			node.data = node.data;
			node.right = delete(minNode, key);
		}
		if (node == null)
			return null;
		return rebalance(node, key);
	}

	public AVLNode<T> findMinNode(AVLNode<T> node) {
		while (node.left != null)
			node = node.left;
		return node;
	}

	/**
	 * 对左儿子的左子树、右儿子右子树插入引起不平衡，需要一次旋转<br>
	 * 对左儿子的右子树、右儿子左子树插入引起不平衡，需要两次旋转
	 * 
	 * @param node
	 * @return
	 */
	public AVLNode<T> rebalance(AVLNode<T> node, int inserKey) {
		int heightGap = heightGap(node);
		if (heightGap <= 1 && heightGap >= -1) {
			return node;
		}
		if (inserKey < node.key && inserKey < node.left.key) {
			// 左左插入
			return rightRotation(node);
		} else if (inserKey < node.key && inserKey > node.left.key) {
			// 左右插入
			return doubleLRRotation(node);
		} else if (inserKey > node.key && inserKey > node.right.key) {
			// 右右
			return rightRotation(node);
		} else if (inserKey > node.key && inserKey < node.right.key) {
			// 右左
			return doubleRLRotation(node);
		}
		return node;
	}

	/**
	 * 先左旋后右旋<br>
	 * 左儿子的右子树插入节点引起的不平衡
	 * 
	 * @param unbalance
	 * @return
	 */
	public AVLNode<T> doubleLRRotation(AVLNode<T> unbalance) {
		AVLNode<T> node = leftRotation(unbalance.left);
		unbalance.left = node;
		return rightRotation(unbalance);
	}

	/**
	 * 先右旋后左旋
	 * 
	 * @param unbalance
	 * @return
	 */
	public AVLNode<T> doubleRLRotation(AVLNode<T> unbalance) {
		AVLNode<T> node = rightRotation(unbalance.right);
		unbalance.right = node;
		return leftRotation(unbalance);
	}

	/**
	 * 左旋转
	 * 
	 * @param node
	 */
	public AVLNode<T> leftRotation(AVLNode<T> unbalance) {
		AVLNode<T> node = unbalance.right;
		unbalance.right = node.left;
		node.left = unbalance;
		unbalance.height = Math.max(height(unbalance.left), height(unbalance.right)) + 1;
		node.height = Math.max(height(node.left), height(node.right)) + 1;
		return node;
	}

	/**
	 * 左旋转
	 * 
	 * @param node
	 */
	public AVLNode<T> rightRotation(AVLNode<T> unbalance) {
		AVLNode<T> node = unbalance.left;
		unbalance.left = node.right;
		node.left = unbalance;
		unbalance.height = Math.max(height(unbalance.left), height(unbalance.right)) + 1;
		node.height = Math.max(height(node.left), height(node.right)) + 1;
		return node;
	}

	/**
	 * 大于1，表示左子树高度高，需要右旋转<br>
	 * 小于-1，表示右子树高度高，需要左旋转<br>
	 * 
	 * @param node
	 * @return
	 */
	public int heightGap(AVLNode<T> node) {
		return node == null ? -1 : height(node.left) - height(node.right);
	}

	private int height(AVLNode<T> node) {
		return node == null ? -1 : node.height;
	}
}
