package org.yinyayun.other;

/**
 * 构建BKtree用于纠错词<br>
 * 一个节点最多跟N个编辑距离不同的节点,后插入的节点根据编辑距离放入对应的子节点下
 * 
 * @author yinyayun
 *
 */
public class EditDistanceBKTree {
	/**
	 * 决定了一个节点的子节点数量，同时也决定了一个节点保存关联的最大编辑距离是多少<br>
	 */
	private int maxEditDistance;
	private EditDistance editDistance = new EditDistance();
	private BKNode ROOT;

	public EditDistanceBKTree(int maxEditDistance) {
		this.maxEditDistance = maxEditDistance;
	}

	public void buildBKTree(String[] words) {
		for (String word : words) {
			if (ROOT == null) {
				ROOT = new BKNode(word);
			}
		}
	}

	private boolean insertWord(String word) {
		return true;
	}

	public class BKNode {
		String word;
		BKNode nextNodes[];

		public BKNode(String word) {
			this.word = word;
			this.nextNodes = new BKNode[maxEditDistance];
		}
	}
}
