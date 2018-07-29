package org.yinyayun.sort;

import java.util.Comparator;

import org.yinyayun.sort.abs.Sort;

/**
 * 梳排序，冒泡排序的一种改进 特点：冒泡为相邻位置调整<br>
 * 梳排序的特点则为将序列不断的分高低区间，每次调整区间上相对位置元素
 * 
 * @author yinyayun
 *
 */
public class CombSort<T> extends Sort<T> {

	public CombSort(Comparator<T> c) {
		super(c);
	}

	@Override
	public void sort(T[] array) {
		if (array.length <= 1)
			return;
		int size = array.length, highIndex = array.length;
		boolean flag = false;
		while (highIndex > 1 || flag) {
			flag = false;
			// 设置高区间的起始位置
			highIndex = Math.max((int) (highIndex / 1.3f), 1);
			for (int lowIndex = 0; lowIndex + highIndex < size; lowIndex++) {
				if (compare(array[highIndex + lowIndex], array[lowIndex]) > 0) {
					swap(array, lowIndex, highIndex + lowIndex);
					flag = true;
				}
			}
		}
	}

}
