package org.yinyayun.other.pdd;

import java.util.Arrays;

/**
 * 字符串查找
 * 
 * @author yinyayun
 *
 */
public class StringIndex {
	/**
	 * 
	 * @param keys
	 *            ["abc","ef","desf","aed"]
	 * @param find
	 *            "fdes"
	 * @return [1,1]，即从第二个穿的第二个位置出现匹配
	 */
	public static int[] findSubString(String[] keys, String sub) {
		// 当前已经匹配了子串的一部分，记录下面从什么位置开始查找
		int[] ret = { -1, -1 };
		int pos = 0;
		for (int keyIndex = 0; keyIndex < keys.length; keyIndex++) {
			String key = keys[keyIndex];
			boolean startFind = pos != 0;
			for (int index = 0; index < key.length(); index++) {
				char ch = sub.charAt(pos);
				if (ch == key.charAt(index)) {
					if (pos == 0) {
						ret = new int[] { keyIndex, index };
					}
					if (pos == sub.length() - 1) {
						return ret;
					} else {
						pos++;
					}
				} else {
					if (startFind) {
						index = -1;
						startFind = false;
					}
					pos = 0;
					ret = new int[] { -1, -1 };
				}
			}
		}
		if (pos != sub.length()) {
			return new int[] { -1, -1 };
		}
		return ret;
	}

	public static void main(String[] args) {
		int[] ret = findSubString(new String[] { "ab", "abc", "efabc", "de", "fef", "iwd" }, "abcde");
		System.out.println(Arrays.toString(ret));
	}
}
