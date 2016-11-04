package com.lekkimworld.liselejespeedtest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
@SuppressWarnings("serial")
public class Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String strlength = req.getParameter("l");
		int length = 0;
		try {
			length = Integer.parseInt(strlength);
		} catch (Throwable t) {}
		if (length <= 0) length = 2 * 1024 * 1000;
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/plain");
		final PrintWriter pw = resp.getWriter();
		int so_far = 0;
		while (so_far < length) {
			final String uuid = UUID.randomUUID().toString();
			final String send = (so_far + uuid.length() <= length) ? uuid : uuid.substring(0, length - so_far);
			pw.write(send);
			pw.flush();
			so_far += uuid.length();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

}
