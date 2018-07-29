package org.yinyayun.sort;

import java.util.Comparator;

import org.yinyayun.sort.abs.Sort;

/**
 * 快速排序的核心思想：<br>
 * （1）设置基准 （2）从后向前找比基准小位置 （3）从前向后找比基准大的位置 （4）交换位置 <br>
 * 循环进行，直到两个位置重合，一次迭代后小元素靠前大元素靠后
 * 
 * @author yinyayun
 *
 * @param <T>
 */
public class QuickSort<T> extends Sort<T> {

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
			// 从后向前，找到小于基准的位置
			while (l < r && compare(array[r], base) < 0) {
				r--;
			}
			// 从前向后，找到大于基准的位置
			while (l < r && compare(array[l], base) > 0) {
				l++;
			}
			// 对于找到的两个位置进行交换
			if (l < r)
				swap(array, l, r);
		}
		// 分区间进行同样操作
		quickSort(array, left, l - 1);
		quickSort(array, r + 1, right);
	}

}
