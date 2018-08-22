package org.yinyayun.nlp.common;

import java.util.List;

/**
 * 常用字符串操作
 * 
 * @author yinyayun
 *
 */
public class StringUtils {

	/**
	 * list截取，并生成字符串
	 * 
	 * @param builder
	 * @param list
	 * @param s
	 * @param e
	 * @return
	 */
	public static String subListToString(StringBuilder builder, List<String> list, int s, int e) {
		builder.setLength(0);
		for (int i = s; i < e; i++) {
			builder.append(list.get(i));
		}
		return builder.toString();
	}

}
