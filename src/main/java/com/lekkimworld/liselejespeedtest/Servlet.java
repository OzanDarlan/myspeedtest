package com.lekkimworld.liselejespeedtest;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/*")
@SuppressWarnings("serial")
public class Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String send = "latency check";
		final PrintWriter pw = resp.getWriter();
		pw.write(send);

	}
	
	protected void writeString(int length, PrintWriter pw) throws IOException {
		int so_far = 0;
		final String uuid = UUID.randomUUID().toString();
		while (so_far < length) {
			final String send = (so_far + uuid.length() <= length) ? uuid : uuid.substring(0, length - so_far);
			pw.write(send);
			pw.flush();
			so_far += uuid.length();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream ins = req.getInputStream();
		int inputLength = req.getContentLength();
		String send = "upload complete";
		final PrintWriter pw = resp.getWriter();
		pw.write(send);
		System.out.println("Receiving <" + inputLength + "> bytes");
		byte[] bytes = new byte[inputLength];
		int read = 0; 
		while (read < inputLength) {
			int rc = ins.read(bytes, read, inputLength);
			read += rc;
		}



		System.out.println("Received <" + read + "> bytes");
	}

}
