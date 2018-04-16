package org.yinyayun.structure.tree.kd;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.yinyayun.common.Dimension;

/**
 * KD树
 * 
 * @author yinyayun
 *
 */
public class KDTree {

	public static void main(String[] args) {
		Dimension[] p = { new Dimension(93, 56), new Dimension(85, 76), new Dimension(89, 96), new Dimension(54, 69),
				new Dimension(68, 79) };
		Node root = new KDTree().buildKDTree(p, 1);
		root.print(1);
	}

	/**
	 * 构建KD树
	 * 
	 * @param p
	 *            数据集
	 * @param depth
	 */
	public Node buildKDTree(Dimension[] p, int depth) {
		if (p.length == 0 || p == null) {
			return null;
		}
		if (p.length == 1) {
			return new Node(p[0]);
		}
		// 奇数层则按照垂直划分，即将Dimension的第一个维度对半分
		Dimension[][] splits = depth % 2 == 1 ? //
				splitP(p, true) : splitP(p, false);
		long l = depth % 2 == 1 ? splits[1][0].x : splits[1][0].y;
		// 递归构建左右子树
		Node left = buildKDTree(splits[0], depth + 1);
		Node right = buildKDTree(splits[2], depth + 1);
		return new Node(left, right, splits[1][0], l);
	}

	public void searchKDTree(Node root, Dimension p, int depth) {
		if (root == null) {
			return;
		}
		searchKDTree(root, p, depth);
	}

	/**
	 * 数据集划分
	 * 
	 * @param vertical
	 *            是否根据纵向划分
	 * @return
	 */
	public Dimension[][] splitP(Dimension[] p, boolean vertical) {
		Comparator<Dimension> c = (c1, c2) -> vertical ? (int) (c1.x - c2.x) : (int) (c1.y - c2.y);
		Arrays.sort(p, c);
		Dimension[] p1 = Arrays.copyOfRange(p, 0, p.length / 2);
		Dimension[] p2 = Arrays.copyOfRange(p, p.length / 2, p.length / 2 + 1);
		Dimension[] p3 = null;
		if (p.length / 2 + 1 < p.length) {
			p3 = Arrays.copyOfRange(p, p.length / 2 + 1, p.length);
		}
		return new Dimension[][] { p1, p2, p3 };
	}

	public static class Node {
		public Node left;
		public Node right;
		public long l;// 中间值
		public Dimension data;

		public Node(Node left, Node right, Dimension data, long l) {
			this.left = left;
			this.right = right;
			this.data = data;
			this.l = l;
		}

		public Node(Dimension data) {
			this.data = data;
		}

		public void print(int depth) {
			for (int i = 0; i < depth; i++) {
				System.out.print("  ");
			}
			System.out.print("-|");
			if (data != null) {
				System.out.println(data);
			}
			System.out.println();
			if (left != null) {
				left.print(depth + 1);
			}
			if (right != null) {
				right.print(depth + 1);
			}

		}

	}
}
