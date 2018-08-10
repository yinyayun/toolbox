package org.yinyayun.structure;

import org.junit.Test;
import org.yinyayun.structure.tree.avl.AVLTree;

public class AVLTest {
	@Test
	public void testAVL() {
		AVLTree<Integer> tree = new AVLTree<Integer>();
		int[] arrays = { 9, 16, 12, 11, 6, 4 };
		for (int array : arrays) {
			tree.insert(array, array);
		}
		System.out.println(tree.search(11));
	}
}
