package org.yinyayun.sort;

import java.util.Arrays;
import java.util.Comparator;

public class SortTest {
	public static void main(String[] args) {
		Comparator<Integer> c = (c1, c2) -> c1 - c2;
		Sort<Integer> sort = new BubblingSort<Integer>(c);
		Integer[] arrays = new Integer[] { 10, 2, 70, 4, 23, 24, 56, 6 };
		sort.sort(arrays);
		System.out.println(Arrays.toString(arrays));
	}
}
