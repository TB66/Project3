package com.s3.https.d0730;

import java.util.HashMap;
import java.util.Map;

public class HttpServletRequest extends HttpServlet {
	Map<String, String> headerMap = new HashMap<String, String>();
	Map<String, String> params = new HashMap<String, String>();
	String method;
	String requestUri;
	String protocol;
	

	public HttpServletRequest() {

	}

	public HttpServletRequest(String requestText) {
		// 完成对请求报文的解析

		String[] lines = requestText.split("\\n");
		String[] items = lines[0].split("\\s");
		method = items[0];

		String[] uri = items[1].split("\\u003F");
		requestUri = uri[0];
		if (uri.length > 1) {
			String[] parameters = uri[1].split("&");
			for (int i = 0; i < parameters.length; i++) {
				String[] parameter = parameters[i].split("=");
				params.put(parameter[0], parameter[1]);
			}
		}

		protocol = items[2];

		for (int i = 1; i < lines.length; i++) {
			lines[i] = lines[i].trim();
			if (lines[i].isEmpty()) {
				break;
			}
			items = lines[i].split(":");

			headerMap.put(items[0], items[1].trim());

		}
	}

	// 获取请求方法
	public String getMethod() {
		return method;
	}

	// 获取请求地址
	public String getRequestURI() {
		return requestUri;
	}

	// 获取请求协议版本
	public String getProtocol() {
		return protocol;
	}

	// 获取请求头域值
	public String getHeader(String name) {
		return null;
	}

	// 获取请求参数
	public String getParameter(String name) {
		return params.get(name);
	}

	// 获取cookie
	public Cookie[] getCookies() {
		return null;
	}

}
