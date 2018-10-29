package org.yinyayun.other.pdd;

import java.util.Arrays;

/**
 * 基于有序数字序列查找出现最多的那个数字出现位置和数量
 * 
 * @author yinyayun
 *
 */
public class MaxCountIndexBaseSortedNumber {

	public static int[] find(int[] sortedNumbers) {
		int pos = 0;
		int maxCount = 1;
		int maxCountPos = 0;
		int currentCount = 1;
		int laseNumber = sortedNumbers[pos];
		for (int i = 1; i < sortedNumbers.length; i++) {
			if (sortedNumbers[i] != laseNumber) {
				// 不相等，意味着新的数字统计开始，同时上一个数字的频次要与历史比对
				if (currentCount > maxCount) {
					maxCount = currentCount;
					maxCountPos = pos;
				}
				currentCount = 1;
				pos = i;
				laseNumber = sortedNumbers[i];
			} else {
				currentCount++;
			}
		}
		if (currentCount > maxCount) {
			maxCount = currentCount;
			maxCountPos = pos;
		}
		return new int[] { maxCountPos, maxCount };
	}

	public static void main(String[] args) {
		int[] ret = find(new int[] { 1, 1, 1, 2, 2, 4, 5, 6, 7, 7, 7, 7, 8, 8 });
		System.out.println(Arrays.toString(ret));
	}
}
