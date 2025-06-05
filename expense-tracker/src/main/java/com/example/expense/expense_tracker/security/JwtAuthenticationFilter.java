package com.example.expense.expense_tracker.security;

import java.util.ArrayList;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.expense.expense_tracker.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


@Autowired
private JwtUtil jwtUtil;
	
	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException, java.io.IOException {

	    String authHeader = request.getHeader("Authorization");

	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        String token = authHeader.substring(7);

	        try {
	            String username = jwtUtil.getUsernameFromToken(token);

	            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            	if(!jwtUtil.isTokenExpired(token)) {
	                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
	                        username, null, new ArrayList<>());

	                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

	                SecurityContextHolder.getContext().setAuthentication(authentication);
	            }}
	        } catch (Exception e) {
	            SecurityContextHolder.clearContext();
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

	            response.setContentType("application/json");
	            response.setCharacterEncoding("UTF-8");
	            String jsonResponse = String.format(
	                "{\"status\":\"unautherized\", \"message\":\"Invalid or expired token: %s\"}", 
	                e.getMessage()
	            );
	            response.getWriter().write(jsonResponse);
	            return;
	        }
	    }

	    filterChain.doFilter(request, response);
	}

}
