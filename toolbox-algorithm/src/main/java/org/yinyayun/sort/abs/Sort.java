package org.yinyayun.sort.abs;

import java.util.Comparator;

/**
 * 排序基类
 * 
 * @author yinyayun
 *
 * @param <T>
 */
public abstract class Sort<T> {
	protected Comparator<T> c;

	public Sort(Comparator<T> c) {
		this.c = c;
	}

	public abstract void sort(T[] array);

	public int compare(T t1, T t2) {
		return c.compare(t1, t2);
	}

	public void swap(T[] array, int i, int j) {
		T temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}
