package org.yinyayun.sort;

import java.util.Comparator;

public class BubblingSort<T> extends Sort<T> {
	public BubblingSort(Comparator<T> c) {
		super(c);
	}

	@Override
	public void sort(T[] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 1; j < array.length; j++) {
				if (compare(array[j], array[j - 1]) > 0) {
					swap(array, j, j - 1);
				}
			}
		}
	}

}
