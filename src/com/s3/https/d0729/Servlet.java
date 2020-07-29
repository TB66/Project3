package com.s3.https.d0729;

import java.io.IOException;

public interface Servlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
