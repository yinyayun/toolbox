package org.yinyayun.nlp.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 针对resource/下的资源加载
 * 
 * @author yinyayun
 *
 */
public abstract class ResourceLoader {
	public abstract void loadResource(File file) throws IOException;

	public void loadResources() throws IOException {
		File tmpFile = null;
		String[] resourceNames = resourceNames();
		if (resourceNames == null) {
			return;
		}
		File tmpDir = FileUtils.createTempDirectory();
		for (String resourceName : resourceNames) {
			try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resourceName)) {
				// zip解压
				if (resourceName.endsWith(".zip"))
					tmpFile = unzip(inputStream, tmpDir);
				else
					tmpFile = FileUtils.inputStreamCopyToTmpDir(tmpDir, inputStream, resourceName);
				loadResource(tmpFile);
				tmpFile.delete();
			} finally {
				if (tmpFile != null && tmpFile.exists())
					tmpFile.delete();
			}
		}
	}

	/**
	 * 默认只取一个文件
	 * 
	 * @param inputStream
	 * @param dir
	 * @return
	 */
	private File unzip(InputStream inputStream, File dir) {
		try (ZipInputStream zin = new ZipInputStream(inputStream)) {
			ZipEntry entry = null;
			while ((entry = zin.getNextEntry()) != null) {
				String name = entry.getName();
				File extractFile = new File(dir, name + "-" + System.currentTimeMillis());
				byte[] buffer = new byte[4096];
				try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(extractFile))) {
					int count = -1;
					while ((count = zin.read(buffer)) != -1)
						out.write(buffer, 0, count);
				}
				return extractFile;
			}
			throw new RuntimeException("can not find file in zipFile!");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 要加载的资源名称
	 * 
	 * @return
	 */
	public abstract String[] resourceNames();

	/**
	 * 带参数的ResourceLoader实例动态创建
	 * 
	 * @param clazz
	 * @param parameterTypes
	 * @param parameterValues
	 * @return
	 * @throws Exception
	 */
	public static <T extends ResourceLoader> T createResourceInstance(Class<T> clazz, Object[] parameterValues,
			Class<?>... parameterTypes) throws Exception {
		try {
			T instance = clazz.cast(clazz.getClassLoader().loadClass(clazz.getName()).getConstructor(parameterTypes)
					.newInstance(parameterValues));
			instance.loadResources();
			return instance;
		} catch (SecurityException | NoSuchMethodException | InvocationTargetException | IllegalArgumentException
				| IllegalAccessException | InstantiationException e) {
			throw new Exception(
					"newInstance error,use classload:" + clazz.getClassLoader() + "detail:" + e.getMessage(), e);
		}
	}

	/**
	 * 创建资源实例
	 */
	public static <T extends ResourceLoader> T createResourceInstance(Class<T> clazz) throws Exception {
		try {
			T instance = clazz.cast(clazz.getClassLoader().loadClass(clazz.getName()).newInstance());
			instance.loadResources();
			return instance;
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException e) {
			throw new Exception(
					"newInstance error,use classload:" + clazz.getClassLoader() + "detail:" + e.getMessage(), e);
		}
	}

}
