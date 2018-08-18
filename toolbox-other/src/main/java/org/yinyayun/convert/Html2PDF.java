package org.yinyayun.convert;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.attach.impl.OutlineHandler;
import com.itextpdf.html2pdf.css.media.MediaDeviceDescription;
import com.itextpdf.html2pdf.css.media.MediaType;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfOutline;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.navigation.PdfExplicitDestination;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.font.FontProvider;

/**
 * 将HTML转PDF
 * 
 * @author yinyayun
 *
 */
public class Html2PDF {
	public static int TIMEOUT = 15000;

	/**
	 * 无法根据html标签生成书签，目前采用html标题生成书签
	 * 
	 * @param urls
	 * @param dest
	 * @throws IOException
	 */
	public static void remoteHtmlToPDF(String[] urls, String dest, ElementFilter filter) throws IOException {
		int page = 1;
		ConverterProperties properties = properties(null);
		try (PdfWriter writer = new PdfWriter(dest)) {
			PdfDocument pdf = new PdfDocument(writer);
			PdfOutline outlines = pdf.getOutlines(false);
			try (Document document = new Document(pdf)) {
				for (String url : urls) {
					System.out.println("转换" + url);
					org.jsoup.nodes.Document doc = createConnection(url).get();
					String title = title(doc);
					if (filter != null)
						filter.filter(doc);
					//
					properties.setBaseUri(doc.baseUri());
					List<IElement> elements = HtmlConverter.convertToElements(doc.html(), properties);
					for (IElement element : elements) {
						document.add((IBlockElement) element);
					}
					// 添加书签
					PdfOutline link = outlines.addOutline(title);
					link.addDestination(PdfExplicitDestination.createFit(pdf.getPage(page)));
					page = pdf.getNumberOfPages() + 1;
				}
			}
		}
	}

	/**
	 * 采用PdfDocument进行过渡，之后合并，该方式可以生成书签 ps:多个pdf合并时书签位置未同步
	 * 
	 * @param urls
	 * @param dest
	 * @throws IOException
	 */
	public static void remotePdfsToPDF(String[] urls, String dest, ElementFilter filter) throws IOException {
		ConverterProperties properties = properties(null);
		PdfWriter writer = new PdfWriter(dest);
		PdfDocument pdf = new PdfDocument(writer);
		PdfMerger merger = new PdfMerger(pdf);
		for (String url : urls) {
			System.out.println("转换" + url);
			org.jsoup.nodes.Document doc = createConnection(url).get();
			if (filter != null)
				filter.filter(doc);
			//
			properties.setBaseUri(doc.baseUri());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfDocument temp = new PdfDocument(new PdfWriter(baos));
			HtmlConverter.convertToPdf(doc.html(), temp, properties);
			temp = new PdfDocument(new PdfReader(new ByteArrayInputStream(baos.toByteArray())));
			merger.merge(temp, 1, temp.getNumberOfPages());
			temp.close();
		}
		pdf.close();
	}

	/**
	 * 一起生成PDF
	 * 
	 * @param baseUri
	 * @param src
	 * @param dest
	 * @throws IOException
	 */
	public static void LocalHtmlsToPDF(String baseUri, String[] src, String dest) throws IOException {
		ConverterProperties properties = properties(baseUri);
		try (PdfWriter writer = new PdfWriter(dest)) {
			PdfDocument pdf = new PdfDocument(writer);
			try (Document document = new Document(pdf)) {
				for (String html : src) {
					List<IElement> elements = HtmlConverter.convertToElements(new FileInputStream(html), properties);
					for (IElement element : elements) {
						document.add((IBlockElement) element);
					}
				}
			}
		}
	}

	/**
	 * 合并已有的多个PDF
	 * 
	 * @param baseUri
	 * @param src
	 * @param dest
	 * @throws IOException
	 */
	public static void mergePDFS(File[] pdfs, String dest) throws IOException {
		PdfWriter writer = new PdfWriter(dest);
		try (PdfDocument pdf = new PdfDocument(writer)) {
			PdfMerger merger = new PdfMerger(pdf);
			for (File pdfFile : pdfs) {
				PdfDocument temp = new PdfDocument(new PdfReader(pdfFile));
				merger.merge(temp, 1, temp.getNumberOfPages());
				temp.close();
			}
		}
	}

	public static Connection createConnection(String url) {
		Connection conn = HttpConnection.connect(url);
		conn.timeout(TIMEOUT);
		conn.maxBodySize(5000);
		conn.userAgent(
				" Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
		return conn;
	}

	/**
	 * 一些HTML中含有富文本，富文本对应的链接为相对路径，所以需要指定基础路径
	 * 
	 * @param baseUri
	 * @return
	 */
	private static ConverterProperties properties(String baseUri) {
		ConverterProperties properties = new ConverterProperties();
		FontProvider font = new FontProvider();
		font.addSystemFonts();
		font.addStandardPdfFonts();
		// STSong-Light
		font.addFont("STSong-Light", "Ansi");
		properties.setFontProvider(font);
		MediaDeviceDescription mediaDeviceDescription = new MediaDeviceDescription(MediaType.SCREEN);
		properties.setMediaDeviceDescription(mediaDeviceDescription);
		properties.setCharset("utf-8");
		// 设置基础URL
		properties.setBaseUri(baseUri);
		//
		properties.setOutlineHandler(OutlineHandler.createStandardHandler());
		// properties.setTagWorkerFactory(new DefaultTagWorkerFactory());
		//
		return properties;
	}

	private static String title(org.jsoup.nodes.Document doc) {
		Elements head = doc.getElementsByTag("head");
		// 剔除标题中|分隔符后的网址
		String title = null;
		for (Element element : head) {
			Elements titles = element.getElementsByTag("title");
			if (titles.size() > 0) {
				title = titles.get(0).text();
				int index = title.lastIndexOf('|');
				if (index == -1)
					index = title.lastIndexOf('-');
				if (index != -1)
					title = title.substring(0, index);
			}
		}
		return title;
	}
}
