package com.wbd.spring.security.evaluators;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.wbd.spring.security.dto.SysPermission;
import com.wbd.spring.security.service.SysPermissionService;
import com.wbd.spring.security.service.SysRoleService;
/**
 * 实现权限的鉴别器，
 * 我们需要自定义对 hasPermission() 方法的处理，就需要自定义 PermissionEvaluator，
 * 创建类 CustomPermissionEvaluator，实现 PermissionEvaluator 接口。
 * @author zgh
 *
 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator{

	@Autowired
	private SysPermissionService  permissionService;
	
	
	@Autowired
	private SysRoleService  roleService;
	
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		//获取到loadUserByUsername()方法的结果
		User user = (User)authentication.getPrincipal();
		
		//获取user中注入的角色
		Collection<GrantedAuthority>  authorities = user.getAuthorities();
		
		//循环所有角色
		for (GrantedAuthority grantedAuthority : authorities) {
		String roleName=	grantedAuthority.getAuthority();
		System.out.println("角色名称===="+roleName);
		//根据角色名称获取角色id
		Integer roleId = roleService.selectByName(roleName).getId();
		
		//根据角色id获取所有权限
		
	List<SysPermission> permissionList=	permissionService.listByRoleId(roleId);
	
	//循环权限列表
	for (SysPermission p : permissionList) {
		
	List p2 = 	p.getPermissions();
	//如果访问的url和权限用户符合，返回true
	
	if(targetDomainObject.equals(p.getUrl())&&p2.contains(permission)) {
		return true;
	}
	
	}
	
		}
		
		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
	
		return false;
	}

}
