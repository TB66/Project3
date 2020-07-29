package com.s3.https.d0729;

public class HttpServlet implements Servlet {
	public void service(HttpServletRequest request, HttpServletResponse response) {

		if ("GET".equals(request.getMethod())) {
			doGet(request, response);
		} else if ("POST".equals(request.getMethod())) {
			doPost(request, response);
		} else if ("PUT".equals(request.getMethod())) {
			doPut(request, response);
		} else {

		}

	}

	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}
}
