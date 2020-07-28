package com.s3.https.d0727;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Tomcat {
	public static void main(String[] args) throws IOException {

		ServerSocket tomcat = new ServerSocket(8080);
		System.out.println("服务器启动成功，端口：8080");
		boolean running=true;
		while (running) {
			Socket socket = tomcat.accept();
			new Thread() {
				public void run() {
					try {
						System.out.println("收到请求");
						InputStream in = socket.getInputStream();
						OutputStream out = socket.getOutputStream();
						byte[] buffer = new byte[1024];
						int count;
						count = in.read(buffer);
						if (count > 0) {
							
							String requestText=new String(buffer, 0, count);
							System.out.println(requestText);
							String[] lines=requestText.split("\\n");
							String[] requestLines=lines[0].split("\\s");
							String webpath=requestLines[1];
							String path = "C:/test/photo/"+webpath;
							String contentType;
							int statusCode=200;
							File file=new File(path);
							if(!file.exists()) {
								statusCode=404;
								path="C:/test/photo/404.html";
							}
							if(webpath.endsWith(".js")) {
								contentType="application/javascript" ;
										
							}else if(webpath.endsWith(".css")) {
								contentType="text/css";
							}else {
								contentType="text/html";
							}
							// 响应头行
							out.write("HTTP/1.1 200 OK\n".getBytes());
							// 响应头域
							out.write(("Content-Type: "+contentType+"; charset=utf-8\n").getBytes());
							// 空行
							out.write("\n".getBytes());
							// 实体
							// out.write("<h1>Hello World</h1>".getBytes());
							
							FileInputStream fis = new FileInputStream(path);
							while ((count =fis.read(buffer)) > 0) {
								out.write(buffer, 0, count);
							}
							fis.close();
						}
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}.start();
		}
		tomcat.close();
	}
}
