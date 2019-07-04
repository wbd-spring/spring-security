package com.wbd.spring.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.wbd.spring.security.dto.SysRole;
import com.wbd.spring.security.dto.SysUserRole;
import com.wbd.spring.security.service.SysRoleService;
import com.wbd.spring.security.service.SysUserRoleService;
import com.wbd.spring.security.utils.JwtUtils;
import com.wbd.spring.security.utils.SpringBeanFactoryUtils;

/**
 * jwt过滤器
 * 
 * @author zgh
 *
 */
public class JwtTokenAuthenticationFilter extends BasicAuthenticationFilter {

	private static final PathMatcher pathMatcher = new AntPathMatcher();

	static final String USER_ID_KEY = "USER_ID";

	public JwtTokenAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);

	}

	// 只对/api/*下请求拦截
	private boolean isProtectedUrl(HttpServletRequest request) {
		return pathMatcher.match("/api/**", request.getServletPath());
	}

	// 验证token
	// 成功返回包含角色的UsernamePasswordAuthenticationToken；失败返回null

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

		Collection<GrantedAuthority> authorities = new ArrayList<>();
		String token = request.getHeader("Authorization");
		if (token != null) {

			Map map = JwtUtils.unSign(token);

			Integer userId = (Integer) map.get(USER_ID_KEY);

			if (userId != null) {

				request.setAttribute(USER_ID_KEY, userId);

				// 从数据库中获取用户信息

				SysUserRoleService urs = SpringBeanFactoryUtils.getBean(SysUserRoleService.class);

				SysRoleService rs = SpringBeanFactoryUtils.getBean(SysRoleService.class);

				List<SysUserRole> list = urs.listByUserId(userId);

				for (SysUserRole sysUserRole : list) {
					SysRole role = rs.selectById(sysUserRole.getRoleId());

					authorities.add(new SimpleGrantedAuthority(role.getName()));

				}
				//这里直接注入角色，因为JWT已经验证了用户合法性，所以principal和credentials直接为null即可
				return new UsernamePasswordAuthenticationToken(null, null,authorities);

			}
			
			

		}
		
		return null;

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

	if(isProtectedUrl(request)) {
		//验证token、返回对应的token
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

		// 如果验证失败，设置异常；否则将UsernamePasswordAuthenticationToken注入到框架中
        if (authentication == null) {
            //手动设置异常
            request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", new AuthenticationCredentialsNotFoundException("权限认证失败"));
            // 转发到错误Url
            request.getRequestDispatcher("/login/error").forward(request, response);
        } else {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        }

		
		
	}
		
	
	
		
	}

}
