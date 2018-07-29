package org.yinyayun;

import java.util.Arrays;
import java.util.Comparator;

public class ArraySort {
	public static void main(String[] args) {
		Integer[] array = { 10, 2, 3, 55, 32 };
		Comparator<Integer> com = (c1, c2) -> c1 - c2;
		Arrays.sort(array, 0, 3, (c1, c2) -> -com.compare(c1, c2));
		System.out.println(Arrays.toString(array));
	}
}
