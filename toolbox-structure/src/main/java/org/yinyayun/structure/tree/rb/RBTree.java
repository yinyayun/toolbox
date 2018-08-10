package org.yinyayun.structure.tree.rb;

import org.yinyayun.structure.tree.bst.BSTNode;
import org.yinyayun.structure.tree.bst.BSTree;

/**
 * 红黑树<br>
 * 任何一个节点都有颜色，黑色或者红色<br>
 * 根节点是黑色的 <br>
 * 父子节点之间不能出现两个连续的红节点<br>
 * 任何一个节点向下遍历到其子孙的叶子节点，所经过的黑节点个数必须相等 空节点被认为是黑色的<br>
 * 
 * 
 * 红黑树新插入节点都是红色，插入修复必要条件为父节点为红节点，主要分为是四种场景<br>
 * (1)叔叔节点为红节点<br>
 * (2)叔叔节点为空，红节点处于左子树<br>
 * (3)叔叔节点为空，红节点处于右子树<br>
 * (4)叔叔节点为空，红节点不处于一条斜线上，如左子节点为红，左子节点的右子节点为红<br>
 * 第一种场景，需要将父节点和叔叔节点的颜色与祖父节点颜色调换,如果祖父节点的父节点也是红色，则继续修复<br>
 * 第二/三种场景，只需要进行一次右旋/左旋<br>
 * 第四种场景，需要进行两次旋转<br>
 * 
 * @author yinyayun
 *
 */
public class RBTree<T> extends BSTree<T> {
	public final static boolean RED = true;
	public final static boolean BLACK = false;

	/**
	 * 移除
	 * 
	 * @param node
	 * @param key
	 * @return
	 */
	public RBNode<T> remove(RBNode<T> node, int key) {
		return null;
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

	@Override
	public void afterLeftRolation(BSTNode<T> unbalance, BSTNode<T> node) {
		RBNode<T> rbNode = (RBNode<T>) node;
		RBNode<T> rbUnbalance = (RBNode<T>) unbalance;
		rbNode.color = rbUnbalance.color;
		rbUnbalance.color = RED;
	}

	@Override
	public void afterRightRolation(BSTNode<T> unbalance, BSTNode<T> node) {
		RBNode<T> rbNode = (RBNode<T>) node;
		RBNode<T> rbUnbalance = (RBNode<T>) unbalance;
		rbNode.color = rbUnbalance.color;
		rbUnbalance.color = RED;
	}
}
