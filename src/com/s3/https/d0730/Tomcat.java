package com.s3.https.d0730;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Tomcat {
	private HashMap<String, Servlet> servletMap;

	public void startup() throws IOException {
		// 服务器初始化Servlet容器==> Map集合==> URL :Servlet对象

		servletMap = new HashMap<>();
		servletMap.put("/hello", new HelloServlet());
		servletMap.put("/", new ToIndexServlet());
		servletMap.put("/cookie", new CookieServlet());
		servletMap.put("/login.jsp", new LoginPageServlet());
		// 启动服务监听8080端口
		// 循环生成socket对象
		// 使用线程处理浏览器发送的请求

		ServerSocket tomcat = new ServerSocket(8080);
		System.out.println("服务器启动成功，端口：8080");
		boolean running = true;
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
							// 打印请求报文

							String requestText = new String(buffer, 0, count);
							System.out.println(requestText);
							// 解析请求报文

							HttpServletRequest request = buildRequest(requestText);
							HttpServletResponse response = new HttpServletResponse(out);

							// 使用资源地址区分动态请求和静态请求
							// 使用资源地址到Servlet容器中获取Servlet对象

							String uri = request.getRequestURI();
							Servlet servlet = servletMap.get(uri);

							if (servlet != null) {
								// 使用Servlet 处理动态请求

								servlet.service(request, response);

							} else {
								// 如果没有找到对应的Servlet对象，那么将其视为静态请求
								// 处理静态请求
								// 判断资源是否存在，如果不存在返回404
								String webpath = request.getRequestURI();
								String contentType;
								int statusCode = 200;
								String path = "C:/test/photo/" + webpath;

								File file = new File(path);
								if (!file.exists()) {
									statusCode = 404;
									path = "C:/test/photo/404.html";
								}
								if (webpath.endsWith(".js")) {
									contentType = "application/javascript";

								} else if (webpath.endsWith(".css")) {
									contentType = "text/css";
								} else {
									contentType = "text/html";
								}
								// 响应头行
								out.write(("HTTP/1.1 " + statusCode + " OK\n").getBytes());
								// 响应头域
								out.write(("Content-Type: " + contentType + "; charset=utf-8\n").getBytes());
								// 空行
								out.write("\n".getBytes());
								// 实体
								// out.write("<h1>Hello World</h1>".getBytes());

								FileInputStream fis = new FileInputStream(path);
								while ((count = fis.read(buffer)) > 0) {
									out.write(buffer, 0, count);
								}
								fis.close();

							}

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

	/**
	 * 解析请求对象
	 * 
	 * @param requestText
	 * @return
	 */

	private HttpServletRequest buildRequest(String requestText) {
		return new HttpServletRequest(requestText);
	}

	public static void main(String[] args) throws IOException {
		new Tomcat().startup();
	}
}
