package com.s3.https.d0730;

import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		PrintWriter out = response.getWriter();
		if (name != null) {
			out.print("<h1>hello " + name + "!</h1>");
		} else {
			out.print("<h1>please login!</h1>");
		}
	}

}
