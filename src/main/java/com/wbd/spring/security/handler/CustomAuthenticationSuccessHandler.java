package com.wbd.spring.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

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
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 方法的第三个参数 Authentication 为认证后该用户的认证信息
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		logger.info("登录成功{}"+authentication);
		
		response.sendRedirect("/");
	}

}
