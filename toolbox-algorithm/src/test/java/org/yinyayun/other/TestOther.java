package org.yinyayun.other;

import java.util.Arrays;

import org.junit.Test;

public class TestOther {
	/**
	 * 编辑距离测试
	 */
	@Test
	public void testEditDistance() {
		String word = "acive";
		String[] others = { "active", "alive", "dative", "actives", "book" };
		EditDistance distance = new EditDistance();
		for (String other : others) {
			System.out.println(String.format("method1:[%s->%s]=%d", word, other, distance.distance(word, other)));
			System.out
					.println(String.format("method2:[%s->%s]=%d", word, other, distance.distanceOptimize(word, other)));
			System.out.println(String.format("editProcess:[%s->%s]=%s", word, other,
					Arrays.toString(distance.editProcess(word, other))));
		}
	}
}
