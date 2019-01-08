package org.yinyayun.convert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.yinyayun.util.Utils;

/**
 * 
 * @author yinyayun
 *
 */
public class GeneratePDFS {
	 private static Set<String> FILTERS = new HashSet<String>(Arrays.asList(//
	 new String[] { "java", "web", "jvm", "java-concurrency", "http", "zookeeper"
	 }//
	 ));
//	private static Set<String> FILTERS = new HashSet<String>(Arrays.asList(//
//			new String[] { "jvm" }//
//	));

	public static void main(String[] args) throws IOException {
		String baseUri = "http://ningg.top";
		String path = "C:/Users/yinyayun/Desktop/view-source_ningg.top_category_.html";
		// String crawlerUrl = "http://ningg.top/category/";
		// SimpleFilter filter = new SimpleFilter();
		SimpleFilter filter = null;
		String saveDir = "/home/yinyayun/tmp/pdfs";
		Document doc = getDocument(path, baseUri);
		Elements elements = doc.getElementsByAttributeValue("class", "tag_box list-inline");
		List<Menu> menus = new ArrayList<Menu>();
		for (Element element : elements) {
			Elements hrefs = element.getElementsByTag("a");
			for (Element hrefElement : hrefs) {
				String href = hrefElement.attr("href").substring(1);
				String name = hrefElement.childNode(0).toString().trim();
				menus.add(new Menu(href, name));
			}
		}
		for (int i = 0; i < menus.size(); i++) {
			Menu menu = menus.get(i);
			System.out.println(String.format("共计:%d个,正在抓取第：%d个，名称：%s", menus.size(), i + 1, menu.name));
			if (!FILTERS.contains(menu.name)) {
				continue;
			}
			Element next = doc.getElementById(menu.id).nextElementSibling();
			String pdfName = menu.name.replace(" ", "_") + ".pdf";
			parserAndSave(saveDir, pdfName, baseUri, next, filter);
		}
	}

	public static Document getDocument(String htmlPath, String baseUri) throws IOException {
		return Jsoup.parse(new File(htmlPath), "UTF-8");
	}

	public static Document getDocument(String url) throws IOException {
		Connection conn = Html2PDF.createConnection(url);
		return conn.get();
	}

	/**
	 * 解析指定分组，统一保存
	 * 
	 * @param dir
	 * @throws IOException
	 */
	public static void parserAndSave(String dir, String pdfName, String baseUrl, Element element, SimpleFilter filter)
			throws IOException {
		File dest = new File(dir, pdfName);
		if (dest.exists()) {
			System.out.println(pdfName + "已经存在，跳过！");
			return;
		}
		Elements resources = element.getElementsByTag("a");
		List<String> urls = new ArrayList<String>();
		for (Element resource : resources) {
			String href = resource.attr("href");
			urls.add(baseUrl + href);
		}
		Utils.reverse(urls);

		// Html2PDF.remotePdfsToPDF(urls.toArray(new String[0]), dest.getAbsolutePath(),
		// filter);
		Html2PDF.remoteHtmlToPDF(urls.toArray(new String[0]), dest.getAbsolutePath(), filter);

	}

	public static class Menu {
		String id;
		String name;

		public Menu(String id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

		@Override
		public String toString() {
			return "Menu [id=" + id + ", name=" + name + "]";
		}

	}
}
