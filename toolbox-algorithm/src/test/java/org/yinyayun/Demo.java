package org.yinyayun;

import java.util.Arrays;

public class Demo {
	public static void main(String[] args) {
		int[] array = { 7, 8, 6, 4, 9, 1, 2 };
		for (int i = array.length / 2; i >= 0; i--) {
			down(array, i, array.length);
		}
		for (int i = array.length - 1; i > 0; i--) {
			swap(array, 0, i);
			down(array, 0, i);
		}
		System.out.println(Arrays.toString(array));
	}

	public static void down(int[] array, int start, int len) {
		while (start < len) {
			int child = start * 2 + 1;
			if (child >= len) {
				break;
			}
			if (child + 1 < len && array[child + 1] > array[child]) {
				child++;
			}
			if (array[start] < array[child]) {
				swap(array, start, child);
				start = child;
			} else {
				break;
			}
		}
	}

	public static void swap(int[] array, int i, int j) {
		int tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}
}
