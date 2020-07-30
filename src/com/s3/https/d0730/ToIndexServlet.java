package com.s3.https.d0730;

public class ToIndexServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.sendRedirect("/index.html");
	}

}
