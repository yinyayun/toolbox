package org.yinyayun.skiplist;

/**
 * 跳跃表节点
 * 
 * @author yinyayun
 *
 */
public class ListNode {
	ListNode[] forwords;
	int value;

	public ListNode(int maxLevel, int value) {
		this.forwords = new ListNode[maxLevel];
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{").append("value:").append(value).append(",level:").append(forwords.length)
				.append(",nextNodes:[");
		for (int i = 0; i < forwords.length; i++) {
			builder.append(forwords[i].value);
			if (i != forwords.length - 1) {
				builder.append(",");
			}
		}
		builder.append("]}");
		return builder.toString();

	}
}
