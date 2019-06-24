package com.wbd.spring.security.service;

import java.util.List;

import com.wbd.spring.security.dto.SysUserRole;

public interface SysUserRoleService {
	public List<SysUserRole> listByUserId(Integer userId);
}
