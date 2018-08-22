package org.yinyayun.nlp.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * File的常用操作
 * 
 * @author yinyayun
 *
 */
public class FileUtils {
	public final static String TEMP_DIR = "toolbox-nlp-tmp";

	/**
	 * 基于流的拷贝
	 * 
	 * @param tmpDir
	 * @param inputStream
	 * @param optName
	 * @return
	 * @throws IOException
	 */
	public static File inputStreamCopyToTmpDir(File tmpDir, InputStream inputStream, String optName)
			throws IOException {
		File tmpFile = File.createTempFile(optName, ".tmp", tmpDir);
		tmpFile.deleteOnExit();
		Files.copy(inputStream, Paths.get(tmpFile.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
		return tmpFile;
	}

	/**
	 * 创建临时目录
	 * 
	 * @return
	 */
	public static File createTempDirectory() {
		File baseDirectory = new File(System.getProperty("java.io.tmpdir"));
		for (int attempt = 0; attempt < 10; attempt++) {
			File temporaryDirectory = new File(baseDirectory, TEMP_DIR + attempt);
			if (temporaryDirectory.exists() || temporaryDirectory.mkdir()) {
				return temporaryDirectory;
			}
		}
		throw new IllegalStateException("Could not create a temporary directory");
	}

}
