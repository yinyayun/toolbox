package org.yinyayun.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer {
	private String host;
	private int port;
	private Selector selector;

	public NIOServer(String host, int port) {
		this.host = host;
		this.port = port;

	}

	public void startServer() throws IOException {
		ServerSocketChannel channel = ServerSocketChannel.open();
		channel.configureBlocking(false);
		channel.bind(new InetSocketAddress(host, port));
		this.selector = Selector.open();
		// 为该通道绑定OP_ACCEPT事件
		channel.register(this.selector, SelectionKey.OP_ACCEPT);
	}

	public void listen() throws IOException {
		while (true) {
			// 阻塞，知道有新的事件进来
			this.selector.select();
			// 获取所有事件
			Iterator<SelectionKey> selectKeys = this.selector.selectedKeys().iterator();
			while (selectKeys.hasNext()) {
				SelectionKey selectionKey = selectKeys.next();
				if (selectionKey.isAcceptable()) {
					ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.channel();
					SocketChannel channel = serverChannel.accept();
					channel.configureBlocking(false);
					channel.write(ByteBuffer.wrap(new String("服务端：收到连接请求").getBytes()));
					channel.register(this.selector, SelectionKey.OP_READ);
				} else if (selectionKey.isReadable()) {
					SocketChannel chanel = (SocketChannel) selectionKey.channel();
					read(chanel);
				}
				selectKeys.remove();
			}
		}
	}

	private void read(SocketChannel chanel) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		chanel.read(buffer);
		System.out.println(new String(buffer.array()));
	}

}
