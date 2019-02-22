package org.yinyayun.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import com.itextpdf.io.IOException;

public class KeepAliveHttpClient {
	private final String DEFAULT_ENCODE = "utf-8";
	private final Charset DEFAULT_CHARSET = Charset.forName("utf-8");
	private static volatile KeepAliveHttpClient client;
	private MultiThreadedHttpConnectionManager connectionManager;

	private KeepAliveHttpClient(int conn_timeout, int read_timeout, int maxConnection) {
		this.connectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		// 默认每个host允许的最大链接
		params.setDefaultMaxConnectionsPerHost(maxConnection);
		// 最大链接数量
		params.setMaxTotalConnections(maxConnection);
		// 链接超时
		params.setConnectionTimeout(conn_timeout);
		// 等待数据超时
		params.setSoTimeout(read_timeout);
		connectionManager.setParams(params);
	}

	public static KeepAliveHttpClient getClient(int conn_timeout, int read_timeout, int maxConnection) {
		if (client == null) {
			synchronized (KeepAliveHttpClient.class) {
				if (client == null) {
					client = new KeepAliveHttpClient(conn_timeout, read_timeout, maxConnection);
				}
			}
		}
		return client;
	}

	public static KeepAliveHttpClient getFmsClient() {
		return getClient(3000, 3000, 3);
	}

	/**
	 * 
	 * @param input 请求参数
	 * @return
	 * @throws IOException
	 * @throws               java.io.IOException
	 * @throws HttpException
	 */
	public String request(String url, Map<String, Object> params)
			throws IOException, HttpException, java.io.IOException {
		HttpClient httpClient = new HttpClient(this.connectionManager);
		PostMethod postMethod = new PostMethod(url);
		request(postMethod, params);
		try {
			int code = httpClient.executeMethod(postMethod);
			if (code == 200) {
				return new String(postMethod.getResponseBody(), DEFAULT_CHARSET);
			} else {
				throw new IOException("has a http error,error code:" + code);
			}
		} finally {
			postMethod.releaseConnection();
		}
	}

	private void request(PostMethod postMethod, Map<String, Object> inputs) throws IOException, FileNotFoundException {
		Part[] parts = new Part[inputs.size() + 2];
		int fromIndex = 0;
		for (Entry<String, Object> entry : inputs.entrySet()) {
			String field = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof File) {
				parts[fromIndex++] = new FilePart(field, (File) value);
			} else {
				parts[fromIndex++] = new StringPart(field, String.valueOf(value), DEFAULT_ENCODE);
			}
		}
		postMethod.setRequestEntity(new MultipartRequestEntity(parts, postMethod.getParams()));
	}

}
