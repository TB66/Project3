package com.s3.URL.d0725;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DemoURL {
	public static void main(String[] args) throws IOException {
		URL url = new URL("http://47.92.203.144:8080/damai/07-%E5%A4%A7%E9%BA%A6%E5%95%86%E5%9F%8E/index.html");
		System.out.println("协议:"+url.getProtocol()); // 或ur1中的协议
		System.out.println("端口:"+url.getPort()); // 或ur1中的端口
		System.out.println("资源路径:"+url.getPath()); // 或ur1中 的资源路径
		System.out.println("域名:"+url.getHost()); // 或ur1中的域名
		System.out.println("资源名:"+url.getFile()); // 或ur1中的资源名
		System.out.println("地址中参数:"+url.getQuery()); // 或ur1中 的地址中参数

		URLConnection conn = url.openConnection();
		System.out.println("目标资源的最后修改时间:"+conn.getLastModified()); // 目标资源的最后修改时间
		System.out.println("目标资源的大小:"+conn.getContentLengthLong()); // 目标资源的大小.
		System.out.println(" 目标资源的类型:"+conn.getContentType()); // 目标资源的类型jsp htm1 jpg ，
		
		System.out.println("=======");
		//获取输入流
		InputStream in=conn.getInputStream();
		byte[] buffer=new byte[1024];
		int count;
		while((count=in.read(buffer))>0) {
			System.out.println(new String(buffer,0,count));
		}
		in.close();
	}
}
