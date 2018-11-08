package org.yinyayun.skiplist;

import java.util.Random;

/**
 * 跳跃表
 * 
 * @author yinyayun
 *
 */
public class SkipList {
	private ListNode HEAD;
	// 确保TAIL节点的值大于跳跃表中任何一个合法值
	private ListNode TAIL;
	private int maxLevel;
	private int currentLevel = 1;

	public SkipList(int maxLevel) {
		this(maxLevel, new int[0]);
	}

	public SkipList(int maxLevel, int[] sortedKeys) {
		this.maxLevel = maxLevel;
		this.HEAD = new ListNode(maxLevel, Integer.MIN_VALUE);
		this.TAIL = new ListNode(maxLevel, Integer.MAX_VALUE);
		for (int i = 0; i < maxLevel; i++) {
			this.HEAD.forwords[i] = TAIL;
		}
		this.insert(sortedKeys);
	}

	public ListNode search(int key) {
		ListNode x = HEAD;
		// 层级从大到小找大于key值的前一个节点，理论上下一个节点应该是>=key
		for (int level = currentLevel - 1; level >= 0; level--) {
			while (x.forwords[level].value < key) {
				x = x.forwords[level];
			}
		}
		return key == x.forwords[0].value ? x.forwords[0] : null;
	}

	public void insert(int[] sortedKeys) {
		for (int key : sortedKeys) {
			insert(key);
		}
	}

	public ListNode insert(int key) {
		// 记录指向插入位置右边节点的所有指针
		ListNode[] preNodes = new ListNode[maxLevel];
		ListNode x = HEAD;
		// 层级从大到小找大于key值的前一个节点，理论上下一个节点应该是>=key
		for (int level = currentLevel - 1; level >= 0; level--) {
			while (x.forwords[level].value < key) {
				x = x.forwords[level];
			}
			preNodes[level] = x;
		}
		// 如果该key已经存在，则不需要插入,直接返回
		if (x.forwords[0].value == key) {
			return x.forwords[0];
		}
		int nodeLevel = randomLevel();
		// 如果新插入节点的level高于当前list的level
		if (nodeLevel > currentLevel) {
			for (int i = currentLevel; i < nodeLevel; i++) {
				preNodes[i] = HEAD;
			}
			currentLevel = nodeLevel;
		}
		ListNode newNode = new ListNode(nodeLevel, key);
		for (int i = 0; i < nodeLevel; i++) {
			try {
				newNode.forwords[i] = preNodes[i].forwords[i];
				preNodes[i].forwords[i] = newNode;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return newNode;
	}

	/**
	 * 需要更新level
	 * 
	 * @param key
	 * @return
	 */
	public ListNode delete(int key) {
		// 记录指向插入位置右边节点的所有指针
		ListNode[] preNodes = new ListNode[maxLevel];
		ListNode x = HEAD;
		// 层级从大到小找大于key值的前一个节点，理论上下一个节点应该是>=key
		for (int level = currentLevel - 1; level >= 0; level--) {
			while (x.forwords[level].value < key) {
				x = x.forwords[level];
			}
			preNodes[level] = x;
		}
		if (x.forwords[0].value != key) {
			return null;
		}
		// 需要被删除的节点的level数
		x = x.forwords[0];
		int delLevel = x.forwords.length;
		for (int i = 0; i < delLevel; i++) {
			preNodes[i].forwords[i] = x.forwords[i];
		}
		// 更新level
		while (currentLevel > 1 && HEAD.forwords[currentLevel - 1] == TAIL)
			currentLevel--;
		return x;
	}

	private int randomLevel() {
		// 尽可能让一半的节点有多个level
		float p = 0.5f;
		int level = 1;
		Random random = new Random();
		while (random.nextInt(10) / 10f < p && level < maxLevel) {
			level++;
		}
		return level;
	}

	public void print() {
		ListNode x = HEAD;
		while (x.forwords[0] != null) {
			System.out.println(x);
			x = x.forwords[0];
		}
	}
}
