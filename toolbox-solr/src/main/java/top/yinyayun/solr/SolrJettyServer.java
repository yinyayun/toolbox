package top.yinyayun.solr;

import java.io.File;
import java.util.Enumeration;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlConfiguration;

/**
 * 启动solr服务
 * 
 * @author yinyayun
 *
 */
public class SolrJettyServer {

	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 2) {
			throw new RuntimeException("缺少启动参数，{jetty.base} {solr.log.dir}");
		}
		File base = new File(args[0]);
		// set properties
		System.setProperty("solr.log.dir", args[1]);
		System.setProperty("jetty.base", base.getAbsolutePath());
		System.setProperty("solr.solr.home", new File(base.getParentFile(), "solr").getPath());
		//
		LogManager.getLogger(SolrJettyServer.class);
		//
		Resource fileserverXml = Resource.newResource(new File(base, "/etc/jetty.xml"));
		XmlConfiguration configuration = new XmlConfiguration(fileserverXml.getInputStream());
		// 系统配置注入
		Map<String, String> jettyProperties = configuration.getProperties();
		Enumeration<?> props = System.getProperties().propertyNames();
		while (props.hasMoreElements()) {
			String name = (String) props.nextElement();
			jettyProperties.put(name, System.getProperty(name));
		}
		//
		Server server = (Server) configuration.configure();
		server.start();
		server.join();
	}
}
