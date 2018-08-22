
## 汉字相关工具
- 汉字转拼音/拼音转汉字
```java
	try {
		PinyinConvert pinyinConvert = ResourceLoader.createResourceInstance(PinyinConvert.class);
		String[] pinyins = pinyinConvert.convert("化身石桥，受五百年风吹雨打");
		System.out.println(Arrays.toString(pinyins));
	} catch (Exception e) {
		Assert.fail("创建拼音转换实例失败！");
	}
```

- 汉字单元拆分
```java
	try {
		ChineseUnits unit = ResourceLoader.createResourceInstance(ChineseUnits.class);
		String[] units = unit.convert("化身石桥，受五百年风吹雨打");
		System.out.println(Arrays.toString(units));
	} catch (Exception e) {
		Assert.fail("创建汉字单元转换实例失败！");
	}
``` 
- 简繁转换/繁简转换
```java
	try {
		ComplexConvert s2t = ResourceLoader.createResourceInstance(ComplexConvert.class, new Object[] { true },
				boolean.class);
		String traditon = s2t.convert("化身石桥，受五百年风吹雨打");
		System.out.println(traditon);
	} catch (Exception e) {
		Assert.fail("创建汉字简繁转换实例失败！");
	}
```
- 形近音近字获取
```java
		try {
			FNSNWords fnsn = new FNSNWords();
			List<String> similars = fnsn.similarWords("海");
			System.out.println(similars);
		} catch (Exception e) {
			Assert.fail("FNSN实例创建失败！");
		}
```

## 词典来源
- [kfcd/chaizi](https://github.com/kfcd/chaizi)
- [HanLP](https://github.com/hankcs/HanLP)
- [nlp-lang](https://github.com/NLPchina/nlp-lang)