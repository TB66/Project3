package com.s3.thread.d0726;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 单线程下载
 * 
 * @author tanbe
 *
 */
public class SThreadDown {
	public static void main(String[] args) throws IOException {
		URL url = new URL("https://dldir1.qq.com/music/clntupate/QQMusic_YQQWinPCDL.exe");
		URLConnection conn = url.openConnection();
		System.out.println("开始下载");
		InputStream in = conn.getInputStream();
		FileOutputStream out = new FileOutputStream("C:\\test\\QQMusic_YQQWinPCDL1.exe");

		byte[] buffer = new byte[1024];
		int count;
		while ((count = in.read(buffer)) > 0) {
			out.write(buffer, 0, count);
		}
		in.close();
		out.close();
		System.out.println("下载完成");
	}

}
