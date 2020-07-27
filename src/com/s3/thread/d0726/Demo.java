package com.s3.thread.d0726;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 网络基本概念: IP端口 
 * 协议: ISO 7层结构==> TCP/IP ==> socket 
 * URL:用户访问远程服务资源==>服务器资源访间==>Tomcat 服务器 
 * Socket:计算机相互通讯==> QQ 
 * 多线程+ URL ==> 多线程下载(迅雷下载) 
 * 实现过程 
 * 1.单线程下载
 * 2.单线程分块下载
 * 	 	1.获取文件总大小(从conn获取) 
 * 		2.每块的大小(自定义) 
 * 		3.字节流的skip() 跳过N个字节 
 * 3.. 多线程分块下载
 * 		1.进度统计(顺便被打乱) 
 * 		2.合并的时机，要等到所有的块 下载完成才能合并 
 * 		3.时间的统计 
 * 
 * 隐患:对块数的限制12M/2M=6 
 * 1024M / 2M =512 个子线程 
 * 千万别下大文件 
 * 必须要进行块数的限制downNums 不能超过10
 * 
 */
public class Demo {
	private int downNums = 0;

	public static void main(String[] args) throws IOException, InterruptedException {
		String downpath = "https://dldir1.qq.com/music/clntupate/QQMusic_YQQWinPCDL.exe";
		String savepath = "C:\\test\\QQMusic_YQQWinPCDL.exe";
		new Demo().download(downpath, savepath);
	}

	public void download(String downpath, String savepath) throws IOException, InterruptedException {
		URL url = new URL(downpath);
		long time = System.currentTimeMillis();
		URLConnection conn = url.openConnection();
		// 获取文件大小
		int filesize = conn.getContentLength();
		// 定义每块的大小
		int blocksize = 4 * 1024 * 1024;
		// 计算块数
		int blocknums = filesize / blocksize;
		if (filesize % blocksize != 0) {
			blocknums++;
		}
		System.out.println("开始下载");

		for (int i = 0; i < blocknums; i++) {
			downNums++;
			synchronized (this) {
				while(downNums>10) {
					System.out.println("下载块已到达10");
					wait();
				}
			}
			
			int index = i;

			new Thread() {
				public void run() {
					try {
						System.out.println("第" + (index + 1) + "块 开始下载");
						URLConnection conn = url.openConnection();
						InputStream in = conn.getInputStream();
						FileOutputStream out = new FileOutputStream(savepath + index);

						// 开始的字节数
						int beginBytes = index * blocksize;
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
						System.out.println("第" + (index + 1) + "块下载完成");

						synchronized (Demo.this) {
							Demo.this.downNums--;
							Demo.this.notify();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
		synchronized (this) {
			while (downNums > 0) {
				wait();
			}
		}

		marge(savepath, blocknums);
		System.out.println("共用了" + (System.currentTimeMillis() - time) / 1000 + "秒");
		System.out.println("下载完成");
	}

	// 合并
	public static void marge(String path, int filenums) throws IOException {
		FileOutputStream fos = new FileOutputStream(path);
		for (int i = 0; i < filenums; i++) {
			FileInputStream fis = new FileInputStream(path + i);

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
