package org.yinyayun.sort;

import java.util.Comparator;

/**
 * 堆排序
 * 
 * @author yinyayun
 *
 * @param <T>
 */
public class HeapSort<T> extends Sort<T> {
	public HeapSort(Comparator<T> c) {
		super(c);
	}

	@Override
	public void sort(T[] array) {
		// n/2对应节点为最后叶子节点的父节点
		// 依次将最大/最小节点从叶子向上移动，初步构建一个最大/最小堆
		for (int i = array.length / 2; i >= 0; i--) {
			down(array, i, array.length);
		}
		// 此时只能保证父节点大于子节点，但是不能保证左右子书之间的有序性
		// 将父节点与叶子节点交换，之后进行同样的操作，保证两个交换节点之间的这棵树满足最大/最小堆
		for (int i = array.length - 1; i > 0; i--) {
			swap(array, 0, i);
			down(array, 0, i);
		}
	}

	private void down(T[] array, int pos, int length) {
		int childIndex = 2 * pos + 1;
		while (childIndex < length) {
			if (childIndex + 1 < length && c.compare(array[childIndex], array[childIndex + 1]) < 0) {
				childIndex++;
			}
			if (c.compare(array[pos], array[childIndex]) < 0) {
				swap(array, pos, childIndex);
				pos = childIndex;
				childIndex = 2 * pos + 1;
			} else {
				break;
			}
		}
	}

	public void printTree(T[] array) {
		int layer = (int) Math.floor(Math.log(array.length) / Math.log((double) 2)) + 1;
		for (int i = 1; i <= layer; i++) {
			System.out.print(buildString(layer - i));
			int nodes = (int) Math.pow(2, i - 1);
			int start = (int) Math.pow(2, i - 1) - 1;
			for (int j = 1; j <= nodes && start < array.length; j++) {
				System.out.print(array[start++]);
				System.out.print(" ");
			}
			System.out.println();
		}

	}

	private String buildString(int size) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < size; i++) {
			builder.append("-");
		}
		return builder.toString();
	}

}
