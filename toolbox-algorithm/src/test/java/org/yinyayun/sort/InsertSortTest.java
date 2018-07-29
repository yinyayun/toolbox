package org.yinyayun.sort;

import java.util.Arrays;
import java.util.Comparator;

public class InsertSortTest {
	public static void main(String[] args) {
		Integer[] array = { 10, 2, 3, 55, 32 };
		Comparator<Integer> com = (c1, c2) -> c1 - c2;
		InsertSort<Integer> sort = new InsertSort<Integer>(com);
		sort.sort(array);
		System.out.println(Arrays.toString(array));
	}
}
