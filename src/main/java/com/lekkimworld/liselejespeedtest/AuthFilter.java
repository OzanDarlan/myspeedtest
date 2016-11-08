package com.lekkimworld.liselejespeedtest;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class AuthFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
		String key = "lekkim1";
		String value = System.getenv(key);
		System.out.println("<" + key + "> = <" + value + ">");
	}
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		
	}

}
