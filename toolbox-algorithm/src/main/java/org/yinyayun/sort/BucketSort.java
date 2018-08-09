package org.yinyayun.sort;

import java.util.Arrays;
import java.util.Comparator;

import org.yinyayun.sort.abs.Sort;

/**
 * 桶排序
 * 
 * @author yinyayun
 *
 */
public class BucketSort extends Sort<Integer> {
	private int low, max;

	/**
	 * 给定排序数的区间
	 * 
	 * @param low
	 * @param max
	 */
	public BucketSort(int low, int max) {
		super((x, y) -> y - x);
		this.low = low;
		this.max = max;
	}

	/**
	 * 给定排序数的区间
	 * 
	 * @param low
	 * @param max
	 */
	public BucketSort(int low, int max, Comparator<Integer> c) {
		super(c);
		this.low = low;
		this.max = max;
	}

	@Override
	public void sort(Integer[] array) {
		int[] bucket = new int[max - low + 1];
		for (Integer value : array) {
			int index = value - low;
			bucket[index] = bucket[index] + 1;
		}
		int index = 0;
		int limit = array.length;
		boolean desc = c.compare(2, 1) > 0;
		for (int i = bucket.length - 1; i >= 0; i--) {
			if (bucket[i] == 0) {
				continue;
			}
			if (desc) {
				// 降序
				Arrays.fill(array, limit - index - bucket[i], limit - index, i + low);
			} else {
				Arrays.fill(array, index, index + bucket[i], i + low);
			}
			index += bucket[i];
		}
	}

	public static void main(String[] args) {
		Integer[] numbers = { 2, 12, 2, 2, 5, 55, 12, 39, 5, };
		new BucketSort(2, 55).sort(numbers);
		System.out.println(Arrays.toString(numbers));
	}

}
