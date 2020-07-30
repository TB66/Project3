package com.s3.https.d0730;

public class CookieServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 1.向浏览器发送cookie值
		 */
		Cookie cookie = new Cookie("name", "value");
		cookie.setMaxAge(3 * 60);
		response.addCooike(cookie);
		response.getWriter().append("cookie");

	}

}
