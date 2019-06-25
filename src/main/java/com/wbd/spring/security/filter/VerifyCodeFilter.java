package com.wbd.spring.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.DisabledException;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;
/**
 * 1.使用过滤器的思路为：在spring security处理登录验证请求前，验证验证码，如果正确就放行，进行用户名与密码验证
 * ,如果不正常， 就掉到异常
 * 
 * 2.自定义一个过滤器，实现OncePreRequestFilter(该filter保证每次请求一定会过滤)，
 * 在isProtectedUrL()方法中拦截了post方式的/login请求
 * 
 * 3.在处理逻辑中从request中获取验证码， 并进行验证(在session中获取验证码与request中的验证码对比)，如果成功就放行
 * 否则生存异常，继续登录
 * @author zgh
 *
 */
public class VerifyCodeFilter extends OncePerRequestFilter{
	
	private static final PathMatcher pathMatcher = new AntPathMatcher();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//判断是否为login请求， 如果是，进行验证码验证码， 否则放行
		
		if(this.isProtectedUrl(request)) {
			
			String inputVerifyCode = request.getParameter("verifyCode");
			
			if(!this.validateVerifyCode(inputVerifyCode)) {
				 //手动设置异常
                request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION",new DisabledException("验证码输入错误"));
                // 转发到错误Url
                request.getRequestDispatcher("/login/error").forward(request,response);

			}else { //验证码正确， 过滤器继续执行，进行用户名和密码的验证
				filterChain.doFilter(request, response);
			}
			
		}else {
			
			filterChain.doFilter(request, response);
		}
		
	}
	
	
	private boolean validateVerifyCode(String inputVerifyCode) {
		//通过当前线程获取绑定的request对象
		HttpServletRequest request=	((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	   String verifyCode=  (String) request.getSession().getAttribute("validateCode");
	    System.out.println("验证码：="+verifyCode+"输入的验证码：="+inputVerifyCode);
	   return verifyCode.equalsIgnoreCase(inputVerifyCode);
	}
	
	
	//拦截  /login的post请求
	
	private boolean isProtectedUrl(HttpServletRequest request) {
		System.out.println("servlet path=========="+request.getServletPath());
		return "POST".equals(request.getMethod())&&pathMatcher.match("/login", request.getServletPath());
	}
	

}
