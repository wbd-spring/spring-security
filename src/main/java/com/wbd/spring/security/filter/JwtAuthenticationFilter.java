package com.wbd.spring.security.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.wbd.spring.security.utils.JwtUtils;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final String USER_NAME_KEY = "USER_NAME";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			// 从用户请求头中取token，key一般为Authorization
			String token = request.getHeader("Authorization");
			Map map = JwtUtils.unSign(token);
			
			// 将用户名存入request属性
			request.setAttribute(USER_NAME_KEY, map.get(USER_NAME_KEY));
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		    return ;
		}
		
		filterChain.doFilter(request, response);
		
	}
}
