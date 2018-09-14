package org.yinyayun.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOClient {
	private String host;
	private int port;
	private Selector selector;

	public NIOClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void connect() throws IOException {
		SocketChannel channel = SocketChannel.open();
		channel.configureBlocking(false);
		this.selector = Selector.open();
		channel.connect(new InetSocketAddress(host, port));
		channel.register(this.selector, SelectionKey.OP_CONNECT);
		
		while (true) {
			this.selector.select();
			Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
			while (keys.hasNext()) {
				SelectionKey key = keys.next();
				SocketChannel socketChannel = (SocketChannel) key.channel();
				if (key.isConnectable()) {
					if(socketChannel.isConnectionPending()) {
						socketChannel.finishConnect();
					}
					socketChannel.configureBlocking(false);
					socketChannel.write(ByteBuffer.wrap("客户端：连接已经建立".getBytes()));
					socketChannel.register(this.selector, SelectionKey.OP_READ);
				} else if (key.isReadable()) {
					read(socketChannel);
				}
				keys.remove();
			}
		}
	}

	private void read(SocketChannel chanel) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		chanel.read(buffer);
		System.out.println(new String(buffer.array()));
	}
}
