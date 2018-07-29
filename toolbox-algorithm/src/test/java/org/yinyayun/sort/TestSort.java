package org.yinyayun.sort;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.yinyayun.sort.abs.Sort;

import junit.framework.Assert;

public class TestSort {
	private Integer[] array;
	private Integer[] maxArray = { 9, 8, 7, 6, 5, 4, 3, 2, 1 };
	private Comparator<Integer> maxCompare = (c1, c2) -> c1 - c2;

	private boolean equal(Integer[] expected, Integer[] actual) {
		return Arrays.equals(expected, actual);
	}

	@Before
	public void setup() {
		this.array = new Integer[] { 7, 6, 9, 3, 5, 2, 8, 4, 1 };
	}

	@After
	public void tearDown() {
		System.out.println("after sort:" + Arrays.toString(this.array));
	}

	/**
	 * 冒泡
	 */
	@Test
	public void testBubbingSort() {
		System.out.println("BubbingSort>>>>>>");
		Sort<Integer> sort = new BubblingSort<Integer>(maxCompare);
		sort.sort(array);
		Assert.assertTrue(equal(array, maxArray));
	}

	/**
	 * 梳排序
	 */
	@Test
	public void testCombSort() {
		System.out.println("CombSort>>>>>>");
		Sort<Integer> sort = new CombSort<Integer>(maxCompare);
		sort.sort(array);
		Assert.assertTrue(equal(array, maxArray));
	}

}
