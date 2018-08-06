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
     * 另外，如果word1[i]=word2[j],那么必有word1[i]到word2[j]的编辑距离为word1[i-1]到word2[j-1]的编辑距离 <br>
     * 如果word1[i]！=word2[j]，那么他们之间的编辑距离为min(distance[i-1][j],distance[i][j-1],distance[i-1][j-1])+1
     * 
     * @param word1
     * @param word2
     * @return
     */
    public int distance(String word1, String word2) {
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
        return distance[word1.length()][word2.length()];

    }

    private int min(int[][] distance, int i, int j) {
        int minD = Math.min(distance[i - 1][j], distance[i][j - 1]);
        return Math.min(minD, distance[i - 1][j - 1]);
    }

    public static void main(String[] args) {
        String word = "acive";
        String[] others = {"active", "alive", "dative", "actives", "book"};
        EditDistance distance = new EditDistance();
        for (String other : others) {
            System.out.println(String.format("[%s->%s]=%d", word, other, distance.distance(word, other)));
        }

    }
}
