package com.s3.https.d0730;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpServletResponse {
	private int status;
	private String msg;
	private OutputStream out;
	private CharArrayWriter caw = new CharArrayWriter();
	private PrintWriter pw = new PrintWriter(caw);
	private Map<String, String> headerMap = new HashMap<>();
	private List<Cookie> cookieList = new ArrayList<>();

	public HttpServletResponse(OutputStream out) {
		this.out = out;
	}

	// 获取响应输出流(打印流)
	public PrintWriter getWriter() {
		return pw;
	}

	/**
	 * 设置结果码和结果码消息
	 * 
	 * @param status
	 * @param msg
	 */
	public void setStatus(int status, String msg) {
		if (this.status == 0) {
			this.status = status;
			this.msg = msg;
		}
	}

	// 将响应报文推送给浏览器
	public void flushBuffer() throws IOException {
		// 响应头行
		out.write(("HTTP/1.1 " + status + " " + msg + "\n").getBytes());
		// 响应头域
		out.write("Content-Type: text/html; charset=utf-8\n".getBytes());
		for (Entry<String, String> entry : headerMap.entrySet()) {
			out.write((entry.getKey() + ": " + entry.getValue() + "\n").getBytes());
		}

		// 添加cookie
		for (Iterator<Cookie> iterator = cookieList.iterator(); iterator.hasNext();) {
			Cookie cookie = iterator.next();
			out.write(cookie.toString().getBytes());
		}
		// 空行
		out.write("\n".getBytes());
		// 实体
		out.write(caw.toString().getBytes());
	}

	/**
	 * 响应重定向方法
	 */
	public void sendRedirect(String uri) {
		// 设置结果码
		setStatus(301, "Redirect");
		// 头域中写入Location跳转的地址
		headerMap.put("Location", uri);
	}

	public void addCooike(Cookie cookie) {
		cookieList.add(cookie);

	}

}
