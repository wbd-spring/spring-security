package com.wbd.spring.security.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.wbd.spring.security.dto.SysUser;
import com.wbd.spring.security.service.SysUserService;
import com.wbd.spring.security.utils.JwtUtils;
import com.wbd.spring.security.utils.SpringBeanFactoryUtils;
/**
 * 该过滤器在UsernamePasswordAuthenticationFilter之前先执行
 * @author zgh
 *
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter{

	private final String USER_ID_KEY="USER_ID";

	private AuthenticationManager authenticationManager;
	
	public JwtLoginFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager=authenticationManager;
	}
	
	
	/**
	 * 该方法在spring security验证前调用
	 * 将用户信息从request中取出， 并且放入到authenticationManager中
	 */
	@Override
		public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
				throws AuthenticationException {
		 String username = request.getParameter("username");
		 String pwd = request.getParameter("password");
		 return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,pwd,Collections.emptyList()));
		
		}
	
	
	/**
	 * 该方法在spring security验证成功之后调用
	 * 在这个方法中生存jwt token，并且返回给用户
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String username = ((User)authResult.getDetails()).getUsername();
		//通过工具类，利用spring application 来获取SysUserService 接口实现类
		SysUserService ss = SpringBeanFactoryUtils.getBean(SysUserService.class);
		SysUser user = ss.selectByName(username);
		
		//将用户id放入jwt
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put(USER_ID_KEY, user.getId());
		String token = JwtUtils.sign(map, 3600);
		
		//将token放入响应头中
		
		response.addHeader("Authorization", token);
		
	}
	
}
