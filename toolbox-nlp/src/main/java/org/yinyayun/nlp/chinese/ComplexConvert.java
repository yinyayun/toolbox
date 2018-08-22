package org.yinyayun.nlp.chinese;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.yinyayun.nlp.common.ResourceLoader;
import org.yinyayun.structure.trie.TrieMap;
import org.yinyayun.structure.trie.TrieNode;

/**
 * 繁体转换
 * 
 * @author yinyayun
 *
 */
public class ComplexConvert extends ResourceLoader implements AbstractAction<String> {
	private boolean simpleToTraditional;
	private TrieMap<String> t2sDict = new TrieMap<String>();
	private Map<Character, String> complexs = new HashMap<Character, String>();

	public ComplexConvert(boolean simpleToTraditional) {
		this.simpleToTraditional = simpleToTraditional;
	}

	@Override
	public String convert(String text) {
		StringBuilder builder = new StringBuilder();
		if (simpleToTraditional) {
			for (int i = 0; i < text.length(); i++) {
				char ch = text.charAt(i);
				builder.append(complexs.getOrDefault(ch, ch + ""));
			}
			return builder.toString();
		} else {
			return tConvertSimple(builder, text);
		}
	}

	@Override
	public String[] getValues(String word) {
		return new String[] { convert(word) };
	}

	public String tConvertSimple(StringBuilder builder, String text) {
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			TrieNode<String> node = t2sDict.get(ch, t2sDict.getRoot());
			if (node == null) {
				builder.append(ch);
				continue;
			} else {
				ch = node.ENTRYS.length > 0 ? node.ENTRYS[0].value.charAt(0) : ch;
			}
			int next = i + 1;
			TrieNode<String> lastNode = node;
			for (; next < text.length(); next++) {
				node = t2sDict.get(text.charAt(next), lastNode);
				if (node == null) {
					break;
				} else {
					lastNode = node;
				}
			}
			if (lastNode != null && lastNode.ENTRYS.length > 0) {
				builder.append(lastNode.ENTRYS[0].value);
			} else {
				builder.append(ch);
			}
		}
		return builder.toString();
	}

	@Override
	public void loadResource(File file) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (simpleToTraditional)
					loads2t(line);
				else
					loadt2s(line);
			}
		}
	}

	/**
	 * 繁体转简体
	 * 
	 * @param line
	 */
	private void loadt2s(String line) {
		String[] splits = line.split("=");
		if (splits.length == 2) {
			t2sDict.put(splits[0], splits[1]);
		}
	}

	/**
	 * 简体转繁体
	 * 
	 * @param line
	 */
	private void loads2t(String line) {
		String[] splits = line.split(",");
		if (splits.length == 2) {
			complexs.put(splits[0].charAt(0), splits[1]);
		}
	}

	@Override
	public String[] resourceNames() {
		if (simpleToTraditional) {
			return new String[] { "dict/simp-trads.txt" };
		} else {
			return new String[] { "dict/t2s.txt" };
		}
	}

}
