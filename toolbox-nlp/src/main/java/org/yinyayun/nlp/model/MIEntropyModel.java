package org.yinyayun.nlp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.yinyayun.nlp.common.AbstractTokenizer;
import org.yinyayun.nlp.common.StringUtils;
import org.yinyayun.structure.trie.TrieEntry;
import org.yinyayun.structure.trie.TrieNode;

/**
 * 互信息熵模型，用于新词发现
 * 
 * @author yinyayun
 *
 */
public class MIEntropyModel {
	private WordPairCompute wordPair;
	private StringBuilder tmp = new StringBuilder();

	public MIEntropyModel(int ngram, AbstractTokenizer tokenizer) {
		this.wordPair = new WordPairCompute(ngram, tokenizer);
	}

	public void fit(String corpus) {
		fit(corpus, x -> x);
	}

	/**
	 * 模型feed
	 * 
	 * @param corpus
	 * @param filter
	 */
	public void fit(String corpus, Function<String, String> filter) {
		wordPair.compute(filter.apply(corpus));
	}

	/**
	 * 新词发现
	 * 
	 * @param text
	 * @return
	 */
	public List<WordScore> transform(String text, int minCount, int topSize) {
		int ngram = wordPair.ngram;
		List<WordScore> wordScores = new ArrayList<WordScore>();
		List<String> tokens = wordPair.tokenizer(text);
		for (int i = 0; i < tokens.size(); i++) {
			for (int start = Math.max(i - ngram, 0); start < i - 1; start++) {
				WordScore wordScore = score(tokens, start, i, minCount);
				wordScores.add(wordScore);
			}
		}
		Collections.sort(wordScores);
		topSize = Math.min(topSize, wordScores.size());
		return wordScores.subList(0, topSize);
	}

	private WordScore score(List<String> tokens, int start, int end, int minCount) {
		String word = StringUtils.subListToString(tmp, tokens, start, end);
		double total = wordPair.getTotalCount();
		double wordCount = wordPair.wordFreq(word);
		if (wordCount < minCount) {
			return new WordScore(word, -1f);
		}
		// 该组合出现的概率
		double p = wordCount / total;
		double mi = Double.MAX_VALUE;
		for (int i = start + 1; i < end; i++) {
			String leftWord = StringUtils.subListToString(tmp, tokens, start, i);
			String rightWord = StringUtils.subListToString(tmp, tokens, i, end);
			double leftCount = wordPair.wordFreq(leftWord);
			double rightCount = wordPair.wordFreq(rightWord);
			double pLeft = leftCount / total;
			double pRight = rightCount / total;
			// 左右词的互信息熵
			mi = Math.min(mi, p / pLeft * pRight);
		}
		double leftEntropy = entropy(wordPair.leftWords(word));
		double rightEntropy = entropy(wordPair.rightWords(word));
		return new WordScore(word, mi + leftEntropy + rightEntropy);

	}

	/**
	 * 信息熵计算
	 * 
	 * @param node
	 * @return
	 */
	private double entropy(TrieNode<String> node) {
		double entropy = 0f;
		for (TrieEntry<String> entry : node.ENTRYS) {
			double p = entry.count / node.count;
			entropy += p * log2(p);
		}
		return -1 * entropy;
	}

	private double log2(double x) {
		return Math.log(x) / Math.log(2);
	}

	public class WordScore implements Comparable<WordScore> {
		public String word;
		public double score;

		public WordScore(String word, double score) {
			this.word = word;
			this.score = score;
		}

		@Override
		public String toString() {
			return word + "(" + score + ")";
		}

		@Override
		public int compareTo(WordScore o) {
			if (score < o.score) {
				return -1;
			} else if (score > o.score) {
				return 1;
			}
			return 0;
		}
	}

}
