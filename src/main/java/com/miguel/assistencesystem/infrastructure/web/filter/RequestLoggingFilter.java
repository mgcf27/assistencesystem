package com.miguel.assistencesystem.infrastructure.web.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Order(Ordered.LOWEST_PRECEDENCE)
@Component
public class RequestLoggingFilter extends OncePerRequestFilter {
	private static final Logger logger =
	        LoggerFactory.getLogger(RequestLoggingFilter.class);
	

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {
		
		long startTime = System.currentTimeMillis();
		
		String method = request.getMethod();
		
		String uri = request.getRequestURI();
		
		try {
			filterChain.doFilter(request, response);
		}finally {
			int httpStatus = response.getStatus();
			
			long duration = System.currentTimeMillis() - startTime;
			
			logger.info(
					"Request completed method={} path={} status={} durationMs={}",
				    method,
				    uri,
				    httpStatus,
				    duration
					);		
		}		
	}

}
