package com.wbd.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.wbd.spring.security.servlets.VerifyServlet;

@SpringBootApplication
public class SpringSecurityBoot {

	public static void main(String[] args) {

		SpringApplication.run(SpringSecurityBoot.class, args);

	}
	
	//注入一个servlet验证码的bean

	@Bean
	public ServletRegistrationBean registrationBean() {
		
		ServletRegistrationBean srb = new ServletRegistrationBean(new VerifyServlet());
	   srb.addUrlMappings("/getVerifyCode");
	   return srb;
	} 
}
