package org.yinyayun.other;

import java.util.ArrayList;
import java.util.List;

/**
 * 构建BKtree用于纠错词<br>
 * 一个节点最多跟N个编辑距离不同的节点,后插入的节点根据编辑距离放入对应的子节点下
 * 
 * @author yinyayun
 */
public class EditDistanceBKTree {
    private EditDistance editDistance = new EditDistance();
    private BKNode ROOT;

    public List<String> similarityWords(String word, int limitDistance) {
        if (ROOT == null) {
            return null;
        }
        List<String> similaritys = new ArrayList<String>();
        collectWords(similaritys, word, ROOT, limitDistance);
        return similaritys;
    }

    /**
     * 根据三角规则找子节点<br>
     * word是输入的词条，node为当前节点，y为node的子节点<br>
     * d(word,node)<=d(word,y)+d(node,y)<br>
     * d(node,y)>=d(word,node)-d(word,y)，其中d(x,y)最大为limitDistance<br>
     * 
     * @param similaritys
     * @param word
     * @param node
     * @param limitDistance
     */
    private void collectWords(List<String> similaritys, String word, BKNode node, int limitDistance) {
        int distance = editDistance.distanceOptimize(word, node.word);
        if (distance <= limitDistance) {
            similaritys.add(node.word);
        }
        int allowNodeDistance = Math.max(1, distance - limitDistance);
        for (int i = allowNodeDistance - 1; i < node.nextNodes.length; i++) {
            BKNode nextNode = node.nextNodes[i];
            if (nextNode == null) {
                continue;
            }
            else {
                collectWords(similaritys, word, nextNode, limitDistance);
            }
        }
    }

    public void buildBKTree(String[] words) {
        for (String word : words) {
            if (ROOT == null) {
                ROOT = new BKNode(word);
            }
            else {
                insertWord(word, ROOT);
            }
        }
    }

    /**
     * 插入节点
     * 
     * @param word
     * @param node
     */
    private void insertWord(String word, BKNode node) {
        if (word.equals(node.word)) {
            return;
        }
        int distance = editDistance.distanceOptimize(word, node.word);
        // 扩张子节点
        if (distance > node.nextNodes.length) {
            int maxLen = Math.max(distance, node.nextNodes.length * 2);
            BKNode nextNodes[] = new BKNode[maxLen];
            System.arraycopy(node.nextNodes, 0, nextNodes, 0, node.nextNodes.length);
            node.nextNodes = nextNodes;
        }
        // 如果该编辑距离的子节点不存在，则直接填充
        if (node.nextNodes[distance - 1] == null) {
            node.nextNodes[distance - 1] = new BKNode(word);
        }
        else {
            insertWord(word, node.nextNodes[distance - 1]);
        }
    }

    public class BKNode {
        String word;
        BKNode nextNodes[];

        public BKNode(String word) {
            this.word = word;
            this.nextNodes = new BKNode[4];
        }

        @Override
        public String toString() {
            return word;
        }
    }

    public static void main(String[] args) {
        String[] words = {"darling", "dear", "lief", "live", "loving", "toots", "tootsie", "tootsy", "unreliable",
                "dwindle", "flyer", "transient", "rejoice", "paddle", "doorstep", "attributable", "active", "alive",
                "dactive", "book", "can", "sat", "data", "seek", "seat", "ant", "ants", "cont"};
        EditDistanceBKTree tree = new EditDistanceBKTree();
        tree.buildBKTree(words);
        List<String> similaritys = tree.similarityWords("cant", 2);
        System.out.println(similaritys);
    }
}
