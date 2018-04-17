package org.yinyayun.sort;

import java.util.Arrays;
import java.util.Comparator;

public class QuickSort<T> extends Sort<T> {
	public static void main(String[] args) {
		Comparator<Integer> c = (c1, c2) -> c1 - c2;
		QuickSort<Integer> sort = new QuickSort<Integer>(c);
		Integer[] arrays = new Integer[] { 10, 2, 70, 4, 23, 24, 56, 6 };
		sort.sort(arrays);
		System.out.println(Arrays.toString(arrays));
	}

	public QuickSort(Comparator<T> c) {
		super(c);
	}

	@Override
	public void sort(T[] array) {
		quickSort(array, 0, array.length - 1);
	}

	public void quickSort(T[] array, int left, int right) {
		if (left >= right) {
			return;
		}
		int l = left, r = right;
		T base = array[l];
		while (l != r) {
			// 从后向前，小于基准的移动至前面
			while (l < r && compare(array[r], base) < 0) {
				r--;
			}
			while (l < r && compare(array[l], base) > 0) {
				l++;
			}
			if (l < r)
				swap(array, l, r);
		}
		quickSort(array, left, l - 1);
		quickSort(array, r + 1, right);
	}

}
