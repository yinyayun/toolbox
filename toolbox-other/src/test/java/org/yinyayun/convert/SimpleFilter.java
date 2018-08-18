package org.yinyayun.convert;

import org.jsoup.nodes.Element;

public class SimpleFilter extends ElementFilter {

	@Override
	public boolean needFilter(Element node) {
		String name = node.nodeName();
		String id = node.attr("id");
		String className = node.attr("class");
		if ("div".equals(name) && "home-menu".equals(className)) {
			return true;
		}
		if ("disqus_container".equals(id)) {
			return true;
		}
		return false;
	}

}
