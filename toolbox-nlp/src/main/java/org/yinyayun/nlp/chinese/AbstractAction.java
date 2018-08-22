package org.yinyayun.nlp.chinese;

public interface AbstractAction<T> {
	public static enum ACTIONS {
		PINYIN, COMPLEX_FONT, UNITS
	}

	public T convert(String text);

	public String[] getValues(String word);
}
