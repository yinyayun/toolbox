package org.yinyayun.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Selector;

/**
 * NIO
 * 
 * @author yinyayun
 *
 */
public class NioDemo {
	public static void main(String[] args) {
		NioDemo demo = new NioDemo();
		demo.testFileChannelWriter();
		// demo.testFileChannel();
	}

	/**
	 * 
	 */
	public void testSelector() {
		try (Selector selector = Selector.open()) {
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 测试文件读取
	 */
	public void testFileChannel() {
		try (RandomAccessFile randomAccessFile = new RandomAccessFile("data/nio.txt", "rw")) {
			FileChannel channel = randomAccessFile.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(24);
			int bytesRead = channel.read(buffer);
			while (bytesRead != -1) {
				// System.out.println("Read " + bytesRead);
				buffer.flip();
				while (buffer.hasRemaining()) {
					System.out.print((char) buffer.get());
				}
				buffer.clear();
				bytesRead = channel.read(buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 测试文件读取
	 */
	public void testFileChannelWriter() {
		String data = "write nio";
		try (RandomAccessFile randomAccessFile = new RandomAccessFile("data/nio.txt", "rw")) {
			FileChannel channel = randomAccessFile.getChannel();
			channel.position(channel.size() - 1);
			ByteBuffer buffer = ByteBuffer.allocate(24);
			buffer.put(data.getBytes());
			buffer.flip();
			while (buffer.hasRemaining()) {
				channel.write(buffer);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
