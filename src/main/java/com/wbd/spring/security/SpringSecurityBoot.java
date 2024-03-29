package com.wbd.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.wbd.spring.security.filter.JwtAuthenticationFilter;
import com.wbd.spring.security.servlets.VerifyServlet;

@SpringBootApplication
@EnableRedisHttpSession // 开启利用redis存储共享的session
public class SpringSecurityBoot {

	public static void main(String[] args) {

		SpringApplication.run(SpringSecurityBoot.class, args);

	}

	// 注入一个servlet验证码的servlet bean

	@Bean
	public ServletRegistrationBean registrationBean() {

		ServletRegistrationBean srb = new ServletRegistrationBean(new VerifyServlet());
		srb.addUrlMappings("/getVerifyCode");
		return srb;
	}

	// 注入一个jwt的filter bean

	@Bean
	public FilterRegistrationBean jwtAuthenticationFilterRegistrationBean() {
		FilterRegistrationBean frb = new FilterRegistrationBean(new JwtAuthenticationFilter());
		// 拦截规则
		frb.addUrlPatterns("/api/*");
		return frb;
	}
}
