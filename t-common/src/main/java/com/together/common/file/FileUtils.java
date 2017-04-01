package com.together.common.file;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;


/**
 * 基于系统文件的通用操作工具类<p>
 * @author LingMin 
 * @date 2014-07-22<br>
 * @version 1.0<br>
 */
public final class FileUtils {
	/**
	 * 构造函数<p
	 */
	private FileUtils() {}
	
	/**
	 * 关闭文件I/O流<p>
	 * @param obj 文件I/O流<br>
	 */
	public static void closeIO(Object obj) {
		try {
			if (CommonUtils.isNotEmptyObject(obj)) {
				if (obj instanceof java.io.InputStream)
					((java.io.InputStream) obj).close();
				else if (obj instanceof java.io.OutputStream)
					((java.io.OutputStream) obj).close();
				else if (obj instanceof java.io.Reader) {
					((java.io.Reader) obj).close();
				}
			}
			obj = null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 判断指定的文件是否存在<p>
	 * @param path 文件路径<br>
	 * @return true:存在 false:不存在<br>
	 */
	public static boolean isFileExist(String path) {
		java.io.File file = new java.io.File(FileUtils.getCurrentOSFilePath(path));
		return file.exists() && file.isFile();
	}
	
	/**
	 * 判断指定的文件夹是否存在<p>
	 * @param path 文件夹路径<br>
	 * @return true:存在 false:不存在<br>
	 */
	public static boolean isDirectoryExist(String path) {
		java.io.File file = new java.io.File(FileUtils.getCurrentOSFilePath(path));
		return file.exists() && file.isDirectory();
	}
	
	/**
	 * 获取指定文件的文件名<p>
	 * @param path 文件<br>
	 * @return 文件名<br>
	 */
	public static String getFileName(String path) {
		java.io.File file = new java.io.File(FileUtils.getCurrentOSFilePath(path));
		return file.exists() && file.isFile() ? file.getName() : "";
	}
	
	/**
	 * 删除指定的单个文件<p>
	 * @param path 文件全路径<br>
	 * @return true:成功 false:失败<br>
	 */
	public static void deleteFile(String path) {
		java.io.File file = new java.io.File(FileUtils.getCurrentOSFilePath(path));
		if (file.exists() && file.isFile()) file.delete();
	}
	
	/**
	 * 判断指定路径文件是否存在，不存在则创建<p>
	 * @param path 文档路径<br>
	 * @throws Exception 异常<br>
	 */
	public static void createFileByPath(String path) {
		if (StringUtils.isNotEmpty(path) && !FileUtils.isFileExist(path)) {
			try {
				String[] deeps = StringUtils.splitStringTokenizer(path, java.io.File.separator);
				String temp = deeps[0];
				for (int i = 1; i < deeps.length; i ++) {
					temp = temp.concat(java.io.File.separator).concat(deeps[i]);
					if (temp.indexOf(".") != -1 && !FileUtils.isFileExist(temp)) {
						new java.io.File(temp).createNewFile();
					} else {
						if (!FileUtils.isDirectoryExist(temp))
							new java.io.File(temp).mkdir();
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * 将指定路径转换为当前系统的文件路径<p>
	 * @param path 文件路径<br>
	 * @return 当前系统文件路径<br>
	 */
	public static String getCurrentOSFilePath(String path) {
		return path.replace("/", java.io.File.separator).replace("\\", java.io.File.separator);
	}
}
