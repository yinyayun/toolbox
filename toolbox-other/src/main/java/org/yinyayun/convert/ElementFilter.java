package org.yinyayun.convert;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 对HTML元素中的特定元素进行过滤
 * 
 * @author yinyayun
 *
 */
public abstract class ElementFilter {
	/**
	 * 遍历body域，挨个确定是否需要过滤
	 * 
	 * @param doc
	 */
	public void filter(Document doc) {
		Elements elements = doc.body().children();
		for (Element element : elements) {
			iters(element);
		}
	}

	/**
	 * 针对父tag和子tag都会进行遍历，如果父tag确定不需要则子tag不会遍历
	 * 
	 * @param node
	 */
	private void iters(Element element) {
		if (needFilter(element)) {
			element.remove();
			return;
		}
		for (Element child : element.children()) {
			iters(child);
		}
	}

	/**
	 * 是否需要进行过滤
	 * 
	 * @param node
	 * @return
	 */
	public abstract boolean needFilter(Element element);
}
