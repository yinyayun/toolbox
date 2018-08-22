package org.yinyayun.nlp.chinese;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.yinyayun.nlp.common.ResourceLoader;

/**
 * FNSN(Form nearly sound nearly)<br>
 * 相似汉字计算,在音近的基础上找形近的词
 * 
 * @author yinyayun
 *
 */
public class FNSNWords {
	// 是否需要是形状相似
	private boolean shape = true;
	// 汉字->拼音
	private PinyinConvert forworadPinyin;
	// 拼音->汉字
	private PinyinConvert reversePinyin;
	// 汉字的单元
	private ChineseUnits unitsResource;

	public FNSNWords() {
		this(true);
	}

	public FNSNWords(boolean shape) {
		this.shape = shape;
		init();
	}

	private void init() {
		try {
			this.forworadPinyin = ResourceLoader.createResourceInstance(PinyinConvert.class);
			this.reversePinyin = ResourceLoader.createResourceInstance(PinyinConvert.class, new Object[] { true },
					boolean.class);
			if (shape)
				this.unitsResource = ResourceLoader.createResourceInstance(ChineseUnits.class);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @return
	 */
	public List<String> similarWords(String word) {
		// 获取同音的字
		String[] pinyins = forworadPinyin.getValues(word);
		List<String> wordsWithSamePinyin = new ArrayList<String>();
		for (String pinyin : pinyins) {
			for (String value : reversePinyin.getValues(pinyin)) {
				if (value.equals(word))
					continue;
				wordsWithSamePinyin.add(value);
			}
		}
		if (!shape)
			return wordsWithSamePinyin;
		List<String> similars = new ArrayList<String>();
		String[] units = unitsResource.getValues(word);
		for (String unit : units) {
			Set<Character> unitsSet = new HashSet<Character>();
			for (int i = 0; i < unit.length(); i++) {
				unitsSet.add(unit.charAt(i));
			}
			Iterator<String> iters = wordsWithSamePinyin.iterator();
			while (iters.hasNext()) {
				String similar = iters.next();
				String[] similarWordUnits = unitsResource.getValues(similar);
				for (String similarWordUnit : similarWordUnits) {
					float similarity = similarity(unitsSet, unit, similarWordUnit);
					if (similarity >= 0.5f) {
						similars.add(similar);
						iters.remove();
						break;
					}
				}
			}
		}
		return similars;
	}

	private float similarity(Set<Character> unitsSet, String unit, String compareUnit) {
		float count = 0;
		for (int i = 0; i < compareUnit.length(); i++) {
			if (unitsSet.contains(compareUnit.charAt(i))) {
				count++;
			}
		}
		return count * 2f / (float) (unit.length() + compareUnit.length());
	}

	public static void main(String[] args) {
		FNSNWords similarWords = new FNSNWords();
		String[] words = { "春", "晓", "似" };
		for (String word : words) {
			System.out.println(similarWords.similarWords(word));
		}
	}

}
