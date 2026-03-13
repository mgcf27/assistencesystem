package com.miguel.assistencesystem.infrastructure.web.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.miguel.assistencesystem.infrastructure.security.context.AuthenticationContext;
import com.miguel.assistencesystem.infrastructure.security.session.SessionManager;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class AuthenticationFilter extends OncePerRequestFilter {
	private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

	private final SessionManager sessionManager;
	
	public AuthenticationFilter(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
	
	
	
	@Override
	protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
			throws ServletException, IOException{
		
		log.debug("AuthenticationFilter executed for {}", request.getRequestURI());
		try {
			String header = request.getHeader(AUTHORIZATION_HEADER);
			
			if(header == null || !header.startsWith(BEARER_PREFIX)) {
				filterChain.doFilter(request, response);
				return;
			}
			
			String token = header.substring(BEARER_PREFIX.length());
			
			if(token.isBlank()) {
				filterChain.doFilter(request, response);
				return;
			}
			
			sessionManager.resolveIdentityFromToken(token)
				.ifPresent(AuthenticationContext::set);
			
			filterChain.doFilter(request, response);
		} finally {
			AuthenticationContext.clear();
		}
	}	
}
