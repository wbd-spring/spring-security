package com.wbd.spring.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
/**
 * 这就是 Spring Security 的默认退出配置，Spring Security 在退出时候做了这样几件事：

使当前的 session 失效
清除与当前用户有关的 remember-me 记录
清空当前的 SecurityContext
重定向到登录页
Spring Security 默认的退出 Url 是 /logout，我们可以修改默认的退出 Url，例如修改为 /signout：

 * @author zgh
 *
 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
	Logger log = LoggerFactory.getLogger(getClass());
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String username = ((User)authentication.getPrincipal()).getUsername();
		log.info("退出成功，用户名：{}",username);
		//重定向到登录页面
		response.sendRedirect("/login");
	}

}
