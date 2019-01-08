package org.yinyayun.convert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BootStrapToPDF {

	public static void main(String[] args) throws IOException {
		String baseUri = "https://getbootstrap.com/";
		String path = "https://getbootstrap.com/docs/4.2/getting-started/introduction/";
		ElementFilter2One filter = d -> d.getElementsByTag("main").get(0);
		String saveDir = "/home/yinyayun/tmp/pdfs";
		Document doc = getDocument(path);
		Elements elements = doc.getElementsByClass("bd-toc-item");
		List<String> urls = new ArrayList<String>();
		for (int i = 0; i < elements.size() - 1; i++) {
			Elements lis = elements.get(i).getElementsByTag("li");
			for (Element li : lis) {
				Element hrefTag = li.getElementsByTag("a").get(0);
				String href = hrefTag.attr("href");
				urls.add(baseUri + "/" + href);
			}
		}
		File dest = new File(saveDir, "bootstrap4.pdf");
		if (dest.exists()) {
			dest.delete();
		}
		Html2PDF.remoteHtmlToPDF(urls.toArray(new String[0]), dest.getAbsolutePath(), filter, 30000, "192.168.16.187",
				8080);

	}

	public static Document getDocument(String htmlPath, String baseUri) throws IOException {
		return Jsoup.parse(new File(htmlPath), "UTF-8");
	}

	public static Document getDocument(String url) throws IOException {
		Connection conn = Html2PDF.createConnection(url, 15000, "192.168.16.232", 8080);
		return conn.get();
	}

}
