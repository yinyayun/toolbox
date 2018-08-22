package org.yinyayun.nlp;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.yinyayun.nlp.chinese.ChineseUnits;
import org.yinyayun.nlp.chinese.ComplexConvert;
import org.yinyayun.nlp.chinese.FNSNWords;
import org.yinyayun.nlp.chinese.PinyinConvert;
import org.yinyayun.nlp.common.ResourceLoader;

import junit.framework.Assert;

public class ChineseTest {
	@Test
	public void testPinyinConvert() {
		try {
			PinyinConvert pinyinConvert = ResourceLoader.createResourceInstance(PinyinConvert.class);
			String[] pinyins = pinyinConvert.convert("化身石桥，受五百年风吹雨打");
			System.out.println(Arrays.toString(pinyins));
		} catch (Exception e) {
			Assert.fail("创建拼音转换实例失败！");
		}
	}

	@Test
	public void testChineseUnit() {
		try {
			ChineseUnits unit = ResourceLoader.createResourceInstance(ChineseUnits.class);
			String[] units = unit.convert("化身石桥，受五百年风吹雨打");
			System.out.println(Arrays.toString(units));
		} catch (Exception e) {
			Assert.fail("创建汉字单元转换实例失败！");
		}
	}

	@Test
	public void testS2T() {
		try {
			ComplexConvert s2t = ResourceLoader.createResourceInstance(ComplexConvert.class, new Object[] { true },
					boolean.class);
			String traditon = s2t.convert("化身石桥，受五百年风吹雨打");
			System.out.println(traditon);
		} catch (Exception e) {
			Assert.fail("创建汉字简繁转换实例失败！");
		}
	}

	@Test
	public void testFNSN() {
		try {
			FNSNWords fnsn = new FNSNWords();
			List<String> similars = fnsn.similarWords("海");
			System.out.println(similars);
		} catch (Exception e) {
			Assert.fail("FNSN实例创建失败！");
		}
	}
}
