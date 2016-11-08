package com.lekkimworld.liselejespeedtest;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class AuthFilter implements Filter {
	// declarations
	private String username = null;
	private String password = null;
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.username = System.getenv("speedtest.username");
		this.password = System.getenv("speedtest.password");
		System.out.println(this.username);
		System.out.println(this.password);
	}
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		if (null == this.username || this.username.length() == 0 || null == this.password || this.password.length() == 0) {
			chain.doFilter(req, resp);
			return;
		}
		
		final String auth = ((HttpServletRequest)req).getHeader("Authorization");
		if (null == auth || auth.length() == 0) {
			((HttpServletResponse)resp).sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		if (!auth.startsWith("Basic ")) {
			((HttpServletResponse)resp).sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		try {
			final String encodedAuth = auth.substring(6);
			System.out.println(encodedAuth);
			final String decodedAuth = new String(Base64.getDecoder().decode(encodedAuth));
			System.out.println(decodedAuth);
			final int idx = decodedAuth.indexOf(':');
			final String username = decodedAuth.substring(0, idx);
			final String password = decodedAuth.substring(idx+1);
			System.out.println(username);
			System.out.println(password);
			if (this.username.equals(username) && this.password.equals(password)) {
				chain.doFilter(req, resp);
				return;
			}
		} catch (Throwable t) {}
		((HttpServletResponse)resp).sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

}
