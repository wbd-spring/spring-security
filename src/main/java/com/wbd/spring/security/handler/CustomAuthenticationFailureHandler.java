package com.wbd.spring.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * 自定义登录/失败成功之后的处理
 * 有些时候我们想要在认证成功后做一些业务处理，例如添加积分；有些时候我们想要在认证失败后也做一些业务处理，例如记录日志。

在之前的文章中，关于认证成功、失败后的处理都是如下配置的：
http.authorizeRequests()
	// 如果有允许匿名的url，填在下面
//    .antMatchers().permitAll()
	.anyRequest().authenticated().and()
	// 设置登陆页
	.formLogin().loginPage("/login")
	.failureUrl("/login/error")
	.defaultSuccessUrl("/")
	.permitAll()
	...;

即 failureUrl() 指定认证失败后Url，defaultSuccessUrl() 指定认证成功后Url。我们可以通过设置 successHandler() 和 failureHandler() 来实现自定义认证成功、失败处理。

PS：当我们设置了这两个后，需要去除 failureUrl() 和 defaultSuccessUrl() 的设置，否则无法生效。这两套配置同时只能存在一套。

 * @author zgh
 *
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{

	   @Autowired
	    private ObjectMapper objectMapper;

	    private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		logger.info("登录失败===");
		
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(exception.getMessage()));

	}

}
