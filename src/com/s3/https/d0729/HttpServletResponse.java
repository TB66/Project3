package com.s3.https.d0729;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class HttpServletResponse {
	private int status;
	private String msg;
	private OutputStream out;
	private CharArrayWriter caw=new CharArrayWriter();
	private PrintWriter pw=new PrintWriter(caw);
	
	public HttpServletResponse(OutputStream out) {
		this.out=out;
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
		this.status = status;
		this.msg = msg;
	}

	// 将响应报文推送给浏览器
	public void flushBuffer() throws IOException {
		// 响应头行
		out.write(("HTTP/1.1 " + status + " "+msg+"\n").getBytes());
		// 响应头域
		out.write("Content-Type: text/html; charset=utf-8\n".getBytes());
		// 空行
		out.write("\n".getBytes());
		// 实体
		out.write(caw.toString().getBytes());
	}
}
