package com.example.springsecutrityJwtdemo.configure;

import java.io.IOException;

import org.hibernate.bytecode.internal.bytebuddy.PrivateAccessorException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.springsecutrityJwtdemo.services.CustomerUserDetailsServices;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
	private CustomerUserDetailsServices userDetailsService;
	private JwtTokenHelper jwtTokenHelper;
	public JWTAuthenticationFilter(CustomerUserDetailsServices userDetailsService,
			JwtTokenHelper jwtTokenHelper) {
		this.userDetailsService= userDetailsService;
		this.jwtTokenHelper=jwtTokenHelper;
		
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authToken=jwtTokenHelper.getToken(request);
		if(null!=authToken)
			
		{
			String username=jwtTokenHelper.getToken(request);
			if (null!=username) {
				UserDetails userDetails=userDetailsService.loadUserByUsername(username);
				if (jwtTokenHelper.validationTokens(authToken, userDetails)) {
					 UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(username, userDetails);
					 authentication.setDetails(new WebAuthenticationDetails(request));
					 SecurityContextHolder.getContext().setAuthentication(authentication);
				}
				
			}
		}
		filterChain.doFilter(request, response);
		
	}
}