package org.yinyayun.sort;

import java.util.Comparator;

import org.yinyayun.sort.abs.Sort;

/**
 * 归并排序核心思想在于合并两个有序列 （1）二分，直到两个序列的长度各位1 （2）合并两个有序列
 * 
 * @author yinyayun
 *
 * @param <T>
 */
public class MergeSort<T> extends Sort<T> {

	public MergeSort(Comparator<T> c) {
		super(c);
	}

	@Override
	public void sort(T[] array) {
		mergesort(array, 0, array.length - 1);
	}

	private void mergesort(T[] array, int first, int end) {
		if (first >= end)
			return;
		int mid = (end + first) / 2;
		mergesort(array, first, mid);
		mergesort(array, mid + 1, end);
		merge(array, first, mid, end);
	}

	/**
	 * 合并有序列
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private void merge(T[] array, int start, int mid, int end) {
		int i = start, j = mid + 1, index = 0;
		Object[] merges = new Object[end - start + 1];
		while (i <= mid && j <= end) {
			if (c.compare(array[i], array[j]) < 0) {
				merges[index++] = array[i++];
			} else {
				merges[index++] = array[j++];
			}
		}
		for (; i <= mid; i++) {
			merges[index++] = array[i];
		}
		for (; j <= end; j++) {
			merges[index++] = array[j];
		}
		for (int from = 0; from < index; from++) {
			array[start + from] = (T) merges[from];
		}
	}
}
