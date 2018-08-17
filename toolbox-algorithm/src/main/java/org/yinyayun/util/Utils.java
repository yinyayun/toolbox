package org.yinyayun.util;

import java.util.List;

public class Utils {
	/**
	 * 数组翻转
	 * 
	 * @param array
	 */
	public static <T> void reverse(T[] array) {
		for (int i = 0, j = array.length - 1; i < j; i++, j--) {
			T tmp = array[i];
			array[i] = array[j];
			array[j] = tmp;
		}
	}

	/**
	 * 集合翻转
	 * 
	 * @param list
	 */
	public static <T> void reverse(List<T> list) {
		for (int i = 0, j = list.size() - 1; i < j; i++, j--) {
			T tmp = list.get(i);
			list.set(i, list.get(j));
			list.set(j, tmp);
		}
	}
}
