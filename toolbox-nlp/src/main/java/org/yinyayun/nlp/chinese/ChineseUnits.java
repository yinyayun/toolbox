package org.yinyayun.nlp.chinese;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.yinyayun.nlp.common.ResourceLoader;

/**
 * 汉字部件单元
 * 
 * @author yinyayun
 *
 */
public class ChineseUnits extends ResourceLoader implements AbstractAction<String[]> {
	private Map<Character, String> complexs = new HashMap<Character, String>();

	@Override
	public String[] convert(String text) {
		String[] rets = new String[text.length()];
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			rets[i] = complexs.getOrDefault(ch, ch + "");
		}
		return rets;
	}

	@Override
	public String[] getValues(String word) {
		String units = complexs.getOrDefault(word.charAt(0), word);
		return units.split(",");
	}

	/**
	 * 一个汉字可能存在多种拆字方式,用逗号进行分割
	 */
	@Override
	public void loadResource(File file) throws IOException {
		StringBuilder builder = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.setLength(0);
				String[] parts = line.split("\t");
				for (int i = 1; i < parts.length; i++) {
					if (builder.length() > 0)
						builder.append(",");
					builder.append(parts[i].replace(" ", ""));
				}
				complexs.put(line.charAt(0), builder.toString());
			}
		}

	}

	@Override
	public String[] resourceNames() {
		return new String[] { "dict/chaizi-jt.txt" };
	}
}
