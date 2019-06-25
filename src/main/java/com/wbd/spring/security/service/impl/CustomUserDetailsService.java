package com.wbd.spring.security.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wbd.spring.security.dto.SysRole;
import com.wbd.spring.security.dto.SysUser;
import com.wbd.spring.security.dto.SysUserRole;
import com.wbd.spring.security.service.SysRoleService;
import com.wbd.spring.security.service.SysUserRoleService;
import com.wbd.spring.security.service.SysUserService;
//实现spring security 的UserDetailsService
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private SysUserService   userService;
	
	@Autowired
	private SysRoleService    roleService;
	
	@Autowired
	private SysUserRoleService   userRoleService;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
	Collection<GrantedAuthority> authorities = new ArrayList<>();
	//查询数据库
	SysUser user =  userService.selectByName(username);
	if(user==null) {
		throw new UsernameNotFoundException("用户名不存在");
	}
	
	//添加权限
	List<SysUserRole> userRoles = userRoleService.listByUserId(user.getId());
	 
	userRoles.forEach(role->{
		SysRole r =	roleService.selectById(role.getRoleId());
		authorities.add(new SimpleGrantedAuthority(r.getName()));
	});
	
	return new User(user.getName(),user.getPassword(),authorities);
	}

}
