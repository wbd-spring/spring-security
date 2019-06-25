package com.wbd.spring.security.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	@RequestMapping("/")
	public String showHome() {
		//通过spring security获取当前用户信息
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		return "home.html";
	}

	@RequestMapping("/login")
	public String showLoing() {

		return "login.html";
	}
	
	@RequestMapping("/admin")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String printAdmin() {
		return "角色为role_admin";
	}
	
	@RequestMapping("/user")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_USER')")
	public String printUser() {
		
		return "角色为role_user";
	}
	
	@RequestMapping("/login/error")
	public void loginError(HttpServletRequest request,HttpServletResponse response) {
		
		response.setContentType("text/html;charset=utf-8");
		
		
		AuthenticationException exception = (AuthenticationException) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
	   try {
		response.getWriter().write(exception.toString()+"错误信息");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
