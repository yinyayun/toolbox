package org.yinyayun.sort;

import java.util.Comparator;

import org.yinyayun.sort.abs.Sort;

/**
 * 冒泡排序 思想：n次循环，每次循环调整相邻两个对象的位置，每进行完一次内循环，将最大或最小的元素移到末尾
 * 
 * @author yinyayun
 *
 * @param <T>
 */
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
