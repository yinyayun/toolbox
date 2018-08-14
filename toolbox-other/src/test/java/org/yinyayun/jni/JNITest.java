package org.yinyayun.jni;

import java.io.IOException;
import java.net.URL;

import org.yinyayun.classloader.MineClassLoader;

/**
 * 测试两个classload加载同一个动态链接文件
 * 
 * @author yinyayun
 *
 */
public class JNITest {
	public static void main(String[] args) throws Exception {
		URL jar = JNITest.class.getClassLoader().getResource("jni.jar");
		String jni = JNITest.class.getClassLoader().getResource("splitsnt.dll").getPath();
		URL[] urls = { jar };

		JNITest test = new JNITest();
		test.load(urls, jni);
		test.load(urls, jni);
	}

	public void load(URL[] urls, String jni) throws IOException {
		MineClassLoader c = new MineClassLoader(urls);
		try {
			Class<?> clazz = c.loadClass("demo2.JNITest");
			Object obj = clazz.newInstance();
			clazz.getMethod("load", String.class).invoke(obj, jni);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			c.close();
		}
	}
}
