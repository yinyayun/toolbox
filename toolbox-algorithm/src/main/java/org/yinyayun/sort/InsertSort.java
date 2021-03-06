package org.yinyayun.sort;

import java.util.Comparator;

import org.yinyayun.sort.abs.Sort;

/**
 * 插入排序
 * 
 * @author yinyayun
 *
 * @param <T>
 */
public class InsertSort<T> extends Sort<T> {

	public InsertSort(Comparator<T> c) {
		super(c);
	}

	/**
	 * 基准位置从1开始，向后移动;<br>
	 * 由基准位置与他前面的`集合对比更换位置
	 */
	@Override
	public void sort(T[] array) {
		for (int i = 1; i < array.length; i++) {
			T base = array[i];
			int j = i - 1;
			for (; j >= 0 && c.compare(base, array[j]) > 0; j--) {
				array[j + 1] = array[j];
			}
			array[j + 1] = base;
		}
	}

}
