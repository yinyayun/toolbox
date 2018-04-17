package org.yinyayun.sort;

import java.util.Arrays;
import java.util.Comparator;

public class SortTest {
	public static void main(String[] args) {
		Comparator<Integer> c = (c1, c2) -> c1 - c2;
		Integer[] arrays = new Integer[] { 10, 2, 70, 4, 23, 24, 56, 6 };
		quickSort(c, arrays);
	}

	public static void quickSort(Comparator<Integer> c, Integer[] arrays) {
		Sort<Integer> sort = new QuickSort<>(c);
		sort.sort(arrays);
		System.out.println(Arrays.toString(arrays));
	}

	public static void bubblingSort(Comparator<Integer> c, Integer[] arrays) {
		Sort<Integer> sort = new BubblingSort<>(c);
		sort.sort(arrays);
		System.out.println(Arrays.toString(arrays));
	}
}
