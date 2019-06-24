package com.wbd.spring.security.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
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
}
