package org.yinyayun.nlp.chinese;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.yinyayun.nlp.common.ResourceLoader;
import org.yinyayun.structure.trie.TrieEntry;
import org.yinyayun.structure.trie.TrieMap;
import org.yinyayun.structure.trie.TrieNode;

/**
 * 拼音转换 优先尝试采用词组的方式进行拼音转换
 * 
 * @author yinyayun
 *
 */
public class PinyinConvert extends ResourceLoader implements AbstractAction<String[]> {
	private TrieMap<String> polyphones = new TrieMap<String>();
	/**
	 * 是否忽略声调
	 */
	private boolean ignoreTone = true;
	/**
	 * 是否为拼音到汉字
	 */
	private boolean isReverse = false;

	public PinyinConvert() {
	}

	/**
	 * 
	 * @param isReverse
	 *            true 拼音到汉字
	 */
	public PinyinConvert(boolean isReverse) {
		this.isReverse = isReverse;
	}

	/**
	 * 
	 * @param isReverse
	 *            true 拼音到汉字
	 * @param ignoreTone
	 */
	public PinyinConvert(boolean isReverse, boolean ignoreTone) {
		this.isReverse = isReverse;
		this.ignoreTone = ignoreTone;
	}

	/**
	 * PS:如果某个字是多音字，那么拼音将以逗号分隔
	 * 
	 * @param text
	 * @return
	 */
	@Override
	public String[] convert(String text) {
		String[] rets = new String[text.length()];
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			TrieNode<String> node = polyphones.get(ch, polyphones.getRoot());
			if (node == null) {
				rets[i] = "NA";
				continue;
			} else {
				rets[i] = node.ENTRYS.length > 0 ? node.ENTRYS[0].value : "NA";
			}
			int next = i + 1;
			TrieNode<String> lastNode = node;
			for (; next < text.length(); next++) {
				node = polyphones.get(text.charAt(next), lastNode);
				if (node == null) {
					break;
				} else {
					lastNode = node;
				}
			}
			if (lastNode != null && lastNode.ENTRYS.length > 0) {
				String[] part = lastNode.ENTRYS[0].value.split(" ");
				int start = i;
				for (; start < next; start++)
					rets[start] = part[start - i];
				i = next - 1;

			}
		}
		return rets;
	}

	@Override
	public String[] getValues(String word) {
		List<String> values = new ArrayList<String>();
		TrieNode<String> node = polyphones.get(word);
		for (TrieEntry<String> entry : node.ENTRYS) {
			for (String value : entry.value.split(",")) {
				values.add(value);
			}
		}
		return values.toArray(new String[0]);
	}

	@Override
	public void loadResource(File file) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
			if (isReverse) {
				reverseLoad(reader);
			} else {
				forwordLoad(reader);
			}
		}
	}

	private void reverseLoad(BufferedReader reader) throws IOException {
		String line = null;
		StringBuilder builder = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			String[] splits = line.split("=");
			if (splits.length != 2) {
				continue;
			}
			String filter = ignoreTone ? filter(builder, splits[1]) : splits[1];
			String[] pinyins = filter.split(",");
			for (String pinyin : pinyins) {
				polyphones.put(pinyin, splits[0]);
			}
		}
	}

	/**
	 * 正向映射，即词条到拼音，允许多音字
	 * 
	 * @param reader
	 * @throws IOException
	 */
	private void forwordLoad(BufferedReader reader) throws IOException {
		String line = null;
		StringBuilder builder = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			String[] splits = line.split("=");
			if (splits.length == 2) {
				String filter = ignoreTone ? filter(builder, splits[1]) : splits[1];
				polyphones.put(splits[0], filter);
			}
		}
	}

	private String filter(StringBuilder builder, String pinyins) {
		Set<String> repeates = new HashSet<String>();
		for (String pinyin : pinyins.split(",")) {
			builder.setLength(0);
			for (String part : pinyin.split(" ")) {
				if (builder.length() > 0)
					builder.append(" ");
				builder.append(part.substring(0, part.length() - 1));
			}
			repeates.add(builder.toString());
		}
		builder.setLength(0);
		for (String repeate : repeates) {
			if (builder.length() > 0)
				builder.append(",");
			builder.append(repeate);
		}
		return builder.toString();
	}

	@Override
	public String[] resourceNames() {
		return new String[] { "dict/pinyin.txt" };
	}
}
