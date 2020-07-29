package com.s3.https.d0729;

import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Hello World");
		PrintWriter out=response.getWriter();
		out.print("<h1>hello world!</h1>");
		
	}

}
