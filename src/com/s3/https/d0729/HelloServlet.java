package com.s3.https.d0729;

public class HelloServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Hello World");
	}

}
