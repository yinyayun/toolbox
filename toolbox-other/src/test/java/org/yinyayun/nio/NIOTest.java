package org.yinyayun.nio;

import java.io.IOException;

public class NIOTest {
	public static void main(String[] args) throws IOException {
		String host = "127.0.0.1";
		int port = 8921;
		NIOServer server = new NIOServer(host, port);
		server.startServer();
		Runnable st = () -> {
			try {
				server.listen();
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		new Thread(st).start();
		//
		NIOClient client = new NIOClient(host, port);
		client.connect();
	}
}
