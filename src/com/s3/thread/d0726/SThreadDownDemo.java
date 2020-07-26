package com.s3.thread.d0726;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 单线程分块下载
 * 
 * @author tanbe
 *
 */
public class SThreadDownDemo {
	public static void main(String[] args) throws IOException {
		URL url = new URL("https://dldir1.qq.com/music/clntupate/QQMusic_YQQWinPCDL.exe");
		String path="C:\\test\\QQMusic_YQQWinPCDL.exe";
		long time=System.currentTimeMillis();
		URLConnection conn = url.openConnection();
		// 获取文件大小
		int filesize = conn.getContentLength();
		// 定义每块的大小
		int blocksize = 6 * 1024 * 1024;
		// 计算块数
		int blocknums = filesize / blocksize;
		if (filesize % blocksize != 0) {
			blocknums++;
		}

		System.out.println("开始下载");

		for (int i = 0; i < blocknums; i++) {
			System.out.println("第" + (i + 1) + "块 开始下载");
			conn = url.openConnection();
			InputStream in = conn.getInputStream();
			FileOutputStream out = new FileOutputStream(path + i);

			// 开始的字节数
			int beginBytes = i * blocksize;
			// 结束的字节数
			int endBytes = beginBytes + blocksize;
			// 结束的字节数不能超过文件大小
			if (endBytes > filesize) {
				endBytes = filesize;
			}
			// 跳过开始的字节数
			in.skip(beginBytes);
			/**
			 * 下载块内的字节数 1.下载 2.判断
			 */
			// 当前下载到的位置
			int position = beginBytes;
			byte[] buffer = new byte[1024];
			int count;
			while ((count = in.read(buffer)) > 0) {
				if (position + count > endBytes) {
					// 计算超出的部分
					int a = position + count - endBytes;
					// 减去超出的部分
					count = count - a;
				}
				out.write(buffer, 0, count);
				// 更新下载的位置==>向前推进
				position += count;
				// 如果下载位置已经到达该块的结束位置
				if (position >= endBytes) {
					break;
				}
			}
			in.close();
			out.close();
			System.out.println("第" + (i + 1) + "块下载完成");

		}

		System.out.println("资源下载完成");
		marge(path, blocknums);
		System.out.println("共用了"+(System.currentTimeMillis()-time)/1000+"秒");
	}
	//合并
	public static void marge(String path,int filenums) throws IOException {
		FileOutputStream fos=new FileOutputStream(path);
		for (int i = 0; i < filenums; i++) {
			FileInputStream fis=new FileInputStream(path+i);
			
			byte[] buffer = new byte[1024];
			int count;
			while ((count = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, count);
				
			}
			fis.close();
		}
		fos.close();
		System.out.println("文件合并完成");
	}
}
