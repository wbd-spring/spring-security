package com.wbd.spring.security.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

/**
 * jwtController
 * @author zgh
 *
 */

@RestController
public class JwtController {
	
	private final String USER_ID_KEY="USER_ID";
	
	/**
	 * 登录失败的处理
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/login/error")
	public Object loginError(HttpServletRequest request,HttpServletResponse response) {
		AuthenticationException e = 
		(AuthenticationException) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
	   return e.toString();
	}
	
	
	@GetMapping("/api/admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Object hellAdmin(@RequestAttribute(value=USER_ID_KEY) Integer userId) {
		
		return "hello world admin your userid="+userId;
	}
	
	@GetMapping("/api/user")
	@PreAuthorize("hasRole('ROLE_USER')")
	public Object hellUser(@RequestAttribute(value=USER_ID_KEY) Integer userId) {
		
		return "HI GOOD  userid="+userId;
	}

}
