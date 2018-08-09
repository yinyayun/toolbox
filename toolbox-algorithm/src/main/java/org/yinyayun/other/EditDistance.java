/**
 * Copyright (c) 2018, yayunyin@126.com All Rights Reserved
 */

package org.yinyayun.other;

/**
 * EditDistance 编辑距离
 * 
 * @author yinyayun
 */
public class EditDistance {
	/**
	 * 构建矩阵，位置0为占位 如：<br>
	 * ----m a t <br>
	 * --0 1 2 3 <br>
	 * m 1 <br>
	 * o 2 <br>
	 * t 3 <br>
	 * 那么distance[i][j] 就可以表示word1的前i个字符变换到word2的前j个字符的编辑距离<BR>
	 * 另外，如果word1[i]=word2[j],那么必有word1[i]到word2[j]的编辑距离为word1[i-1]到word2[j-1]的编辑距离
	 * <br>
	 * 如果word1[i]！=word2[j]，那么他们之间的编辑距离为:<br>
	 * min(distance[i-1][j],distance[i][j-1],distance[i-1][j-1])+1<br>
	 * 即在i-1后插入，要不删除i位置，要不i和j位置替换
	 *
	 * 
	 * @param word1
	 * @param word2
	 * @return
	 */
	public int[][] distanceMatrix(String word1, String word2) {
		int[][] distance = new int[word1.length() + 1][word2.length() + 1];
		for (int i = 0; i < word2.length() + 1; i++)
			// word1的0位置变换到word2的前i个字符都要进行i次的插入，所以编辑距离为i
			distance[0][i] = i;
		for (int i = 1; i < word1.length() + 1; i++) {
			distance[i][0] = i;
			for (int j = 1; j < word2.length() + 1; j++) {
				if (word1.charAt(i - 1) == word2.charAt(j - 1))
					distance[i][j] = distance[i - 1][j - 1];
				else
					distance[i][j] = min(distance, i, j) + 1;
			}
		}
		return distance;
	}

	public int distance(String word1, String word2) {
		return distanceMatrix(word1, word2)[word1.length()][word2.length()];
	}

	public String[] editProcess(String word1, String word2) {
		int[][] distance = distanceMatrix(word1, word2);
		StringBuilder s1 = new StringBuilder();
		StringBuilder s2 = new StringBuilder();
		int i = word1.length();
		int j = word2.length();
		while (i > 0 || j > 0) {
			if (j > 0 && distance[i][j] == distance[i][j - 1] + 1) {
				// 如果word1 i位置到word2 j位置的编辑距离等于word1 i位置到word2上一个位置的距离+1，则说明是插入了一个word2 j
				s1.append("-");
				s2.append(word2.charAt(j - 1));
				j--;
			} else if (i > 0 && distance[i][j] == distance[i - 1][j] + 1) {
				// 需要删除word1的i位置
				s1.append(word1.charAt(i - 1));
				s2.append("-");
				i--;
			} else {
				// 替换，或者不变
				s1.append(word1.charAt(i - 1));
				s2.append(word2.charAt(j - 1));
				i--;
				j--;
			}
		}
		return new String[] { s1.reverse().toString(), s2.reverse().toString() };
	}

	/**
	 * 基于滚动数组优化，因为d[i,.]只和d[i-1,.]相关，所以之前i-1之前的并不需要保存，这样的话就可以节省空间。
	 * 
	 * @param word1
	 * @param word2
	 * @return
	 */
	public int distanceOptimize(String word1, String word2) {
		// d_current和d_before分别用于保存d[i,.],d[i-1,.],即word1前i和前i-1到word2每个位置编辑距离，所以d1和d2的长度都为word2的长度
		int[] d_before = new int[word2.length() + 1];
		int[] d_current = new int[word2.length() + 1];
		for (int i = 0; i < word2.length() + 1; i++)
			// 初始化，即word1 0位置到word2每个位置的编辑距离
			d_before[i] = i;
		for (int i = 1; i < word1.length() + 1; i++) {
			// word1 i位置到word2 位置0的距离
			d_current[0] = i;
			// 下面就是求word1 i位置到word2下一个位置的距离
			for (int j = 1; j < word2.length() + 1; j++) {
				if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
					// word1 i位置到word2的j位置编辑距离为word1上一个位置到word2的上个位置距离
					d_current[j] = d_before[j - 1];
				} else {
					// 主要求word1 i位置到word2的j位置的距离
					// d_current[j-1]表示i位置到word2 的j-1位置的距离
					// d_before[j],word1的i-1位置到woord2 j位置的距离
					// d_before[j - 1],word1 i-1位置到word2 j-1位置的距离
					d_current[j] = Math.min(Math.min(d_current[j - 1], d_before[j]), d_before[j - 1]) + 1;
				}
			}
			// d_current为word1 i位置到word2各个位置的编辑距离,下一轮需要计算word1 i+1到word2各个位置的距离，
			// 那么则需要将d_current赋值给d_before
			int[] tmp = d_before;
			d_before = d_current;
			d_current = tmp;
		}
		return d_before[word2.length()];
	}

	private int min(int[][] distance, int i, int j) {
		int minD = Math.min(distance[i - 1][j], distance[i][j - 1]);
		return Math.min(minD, distance[i - 1][j - 1]);
	}
}
