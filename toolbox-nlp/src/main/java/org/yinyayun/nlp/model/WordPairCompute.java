package org.yinyayun.nlp.model;

import java.util.List;

import org.yinyayun.nlp.common.AbstractTokenizer;
import org.yinyayun.nlp.common.FreqDist;
import org.yinyayun.nlp.common.StringUtils;
import org.yinyayun.structure.trie.TrieMap;
import org.yinyayun.structure.trie.TrieNode;

/**
 * 词对信息计算:左词、右词统计
 * 
 * @author yinyayun
 *
 */
public class WordPairCompute {
	// 词频技术
	private FreqDist<String> freqs = new FreqDist<String>();
	// 左边词
	private TrieMap<String> leftWords = new TrieMap<String>();
	// 右边词
	private TrieMap<String> rightWords = new TrieMap<String>();
	private StringBuilder tmp = new StringBuilder();
	public final int ngram;
	private AbstractTokenizer tokenizer;

	public WordPairCompute(int ngram, AbstractTokenizer tokenizer) {
		this.ngram = ngram;
		this.tokenizer = tokenizer;
	}

	public void compute(String text) {
		List<String> tokens = tokenizer.tokenizer(text);
		int tokenSize = tokens.size();
		for (int i = 0; i < tokenSize; i++) {
			for (int n = 0; n < ngram; n++) {
				int start = i - n;
				if (start < 0) {
					break;
				}
				String sub = StringUtils.subListToString(tmp, tokens, start, i);
				freqs.add(sub);
				if (start > 0)
					leftWords.put(sub, tokens.get(start - 1));
				if (i + 1 < tokenSize)
					rightWords.put(sub, tokens.get(i + 1));
			}
		}
	}

	public TrieNode<String> leftWords(String word) {
		return leftWords.getOrDefault(word, new TrieNode<String>());
	}

	public TrieNode<String> rightWords(String word) {
		return rightWords.getOrDefault(word, new TrieNode<String>());
	}

	public List<String> tokenizer(String text) {
		return tokenizer.tokenizer(text);
	}

	public double getTotalCount() {
		return freqs.total();
	}

	public double wordFreq(String token) {
		return freqs.freq(token);
	}
}
