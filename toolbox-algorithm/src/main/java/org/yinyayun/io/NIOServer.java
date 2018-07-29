package org.yinyayun.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * 模拟NIO服务的实现
 * 
 * @author yinyayun
 *
 */
public class NIOServer {
	public void startServer() {
		try {
			// 创建channel
			ServerSocketChannel channel = ServerSocketChannel.open();
			// 绑定socket端口
			channel.bind(new InetSocketAddress(9999));
			Selector selector = Selector.open();
			// 设置非阻塞，并注册到selector上
			channel.configureBlocking(false);
			channel.register(selector, SelectionKey.OP_ACCEPT);
			while (true) {
				int size = selector.select();
				if (size == 0)
					continue;
				Iterator<SelectionKey> its = selector.selectedKeys().iterator();
				while (its.hasNext()) {
					SelectionKey key = its.next();
					if (key.isConnectable()) {
						System.out.println("连接事件");
					}
					if (key.isAcceptable()) {
						System.out.println("连接建立");
					}
					if (key.isWritable()) {
						System.out.println("通道可写");
					}
					if (key.isReadable()) {
						System.out.println("通道可读");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void connect() {
		
	}
}
