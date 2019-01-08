package org.yinyayun.convert;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@FunctionalInterface
public interface ElementFilter2One {
	public Element convert(Document doc);
}
