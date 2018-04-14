package org.yinyayun;

import org.yinyayun.common.Dimension;
import org.yinyayun.structure.tree.kd.KDTree;
import org.yinyayun.structure.tree.kd.KDTree.Node;

public class KDTreeTest {
	/**
	 * KD树构建
	 */
	public void testBuild() {
		Dimension[] p = { new Dimension(93, 56), new Dimension(85, 76), new Dimension(89, 96), new Dimension(54, 69),
				new Dimension(68, 79) };
		Node root = new KDTree().buildKDTree(p, 1);
		root.print(1);
	}

	/**
	 * KD树的查找
	 */
	public void testSearch() {

	}
}
