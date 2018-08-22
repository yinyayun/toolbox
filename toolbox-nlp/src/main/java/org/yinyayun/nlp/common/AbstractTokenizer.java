package org.yinyayun.nlp.common;

import java.util.List;

/**
 * 分词器
 * 
 * @author yinyayun
 *
 */
public abstract class AbstractTokenizer {
	public abstract List<String> tokenizer(String text);
}
