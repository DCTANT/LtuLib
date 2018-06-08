package com.tstdct.lib;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Dechert on 2018-02-22.
 * Company: www.chisalsoft.co
 */

public class FileUtil {
	public static String readFile(String path) {
		File file = new File(path);
		return readFile(file);
	}

	public static String readFile(File file) {
		if (!file.exists()) {
			return "File not found!!";
		} else {
			byte[] totalBytes = new byte[(int) file.length()];
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				fileInputStream.read(totalBytes, 0, (int) file.length());
				String result = new String(totalBytes, Charset.forName("UTF-8"));
				fileInputStream.close();
				return result;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "Have exceptions!!";
	}

	public static boolean outputFile(String path, String content) {
		content = new String(content.getBytes(), Charset.forName("UTF-8"));
		createPath(path);
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] write = content.getBytes();
			fileOutputStream.write(write, 0, write.length);
			fileOutputStream.flush();
			fileOutputStream.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static void createPath(String path) {
		int index = path.lastIndexOf("/");
		String dirPath = path.substring(0, index + 1);
		File dirs = new File(dirPath);
		if (!dirs.exists()) {
			dirs.mkdirs();
		}
	}

	public static void deleteFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("deleteFile：文件不存在");
		} else {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				String[] children = file.list();
				if (children.length == 0) {
					file.delete();
				} else {
					for (int i = 0; i < children.length; i++) {
						File childFile = new File(file.getAbsolutePath(), children[i]);
						deleteFile(childFile.getAbsolutePath());
					}
				}
			}
		}
	}

	/**
	 * 为了彻底删除文件夹准备的，单纯使用deleteFile无法删除文件夹，只能删光文件夹中所有文件
	 *
	 * @param path
	 */
	public static void deleteAllFiles(String path) {
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("deleteFile：文件不存在");
		} else {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				String[] children = file.list();
				if (children.length > 0) {
					deleteFile(path);
					deleteAllFiles(path);
				} else {
					file.delete();
					//Function end
					return;
				}
			}
		}
	}

	public static void copyFile(String srcPath, String desPath) {
		File srcFile = new File(srcPath);
		File desFile = new File(desPath);
		if (!srcFile.exists()) {
			System.out.println("源文件不存在");
		}
		createPath(desPath);
		if (!desFile.exists()) {
			try {
				desFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileInputStream fileInputStream = new FileInputStream(srcFile);
			FileOutputStream fileOutputStream = new FileOutputStream(desFile);
			byte[] fileBytes = new byte[fileInputStream.available()];
			fileInputStream.read(fileBytes, 0, fileBytes.length);
			fileOutputStream.write(fileBytes, 0, fileBytes.length);
			fileOutputStream.flush();
			fileOutputStream.close();
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean saveBitmap(Bitmap bitmap, String path) {
		File file = new File(path);
		createPath(path);
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static <T> ArrayList<T> jsonArrayToList(String content, Gson gson) {
		ArrayList<T> list = gson.fromJson(content, new TypeToken<ArrayList<T>>() {
		}.getType());
		return list;
	}
}
