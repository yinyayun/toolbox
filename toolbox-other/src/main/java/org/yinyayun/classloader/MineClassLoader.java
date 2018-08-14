package org.yinyayun.classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * 重写classload，主要用于模拟多classload加载jni的场景<br>
 * JVM是不支持多个classload加载同一个jni文件的
 * 
 * @author yinyayun
 *
 */
public class MineClassLoader extends URLClassLoader {
	private static ClassLoader base = Thread.currentThread().getContextClassLoader();

	public MineClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}

	public MineClassLoader(URL[] urls) {
		super(urls, base);
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		synchronized (getClassLoadingLock(name)) {
			// 从已经加载的缓存中加载
			Class<?> clazz = findLoadedClass(name);
			if (clazz != null) {
				if (resolve)
					// 连接
					resolveClass(clazz);
				return clazz;
			}
			// 一些基础的类从父容器加载
			if (name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("com.sun.")) {
				String resourceName = name.replace('.', '/') + ".class";
				if (base.getResource(resourceName) != null) {
					clazz = base.loadClass(name);
					if (clazz != null) {
						if (resolve)
							resolveClass(clazz);
						return clazz;
					}
				}
			}
			clazz = findClass(name);
			if (clazz != null) {
				if (resolve)
					resolveClass(clazz);
				return clazz;
			}
		}
		throw new ClassNotFoundException(name);
	}
}