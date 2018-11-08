package org.yinyayun.skiplist;

import org.junit.Test;

public class TestSkipList {
	@Test
	public void test() {
		SkipList skip = new SkipList(3, new int[] { 1, 3, 6, 8, 9, 11, 14 });
		skip.print();
		System.out.println("===========after delete============");
		skip.delete(9);
		skip.print();
		// ListNode node = skip.search(8);
		// System.out.println(node);
	}
}
