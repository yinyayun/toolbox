package top.yinyayun.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.spatial3d.Geo3DPoint;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * 空间检索
 * 
 * @author yinyayun
 *
 */
public class GeoDemo {
	public static void main(String[] args) throws IOException {
		GeoDemo geo = new GeoDemo();
		geo.createIndex();
	}

	public void search() throws IOException {
		Directory dir = FSDirectory.open(new File("D:\\TMP\\index").toPath());
		IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(dir));
		// searcher.search(query, results);
	}

	public void createIndex() throws IOException {
		Directory dir = FSDirectory.open(new File("D:\\TMP\\index").toPath());
		double[][] p = { { 38.5, 56.3 }, { 40.5, 56.3 }, { 38.5, 60.3 }, { 39.5, 57.3 }, { 90d, 80d } };
		IndexWriterConfig config = new IndexWriterConfig();
		try (IndexWriter writer = new IndexWriter(dir, config)) {
			for (int i = 0; i < p.length; i++) {
				Document doc = new Document();
				doc.add(createGEO(p[i][0], p[i][1]));
				writer.addDocument(doc);
			}
			writer.flush();
		}
	}

	public Geo3DPoint createGEO(double latitude, double longitude) {
		return new Geo3DPoint("pos", latitude, longitude);
	}
}
