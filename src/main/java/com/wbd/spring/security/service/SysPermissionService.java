package com.wbd.spring.security.service;

import java.util.List;

import com.wbd.spring.security.dto.SysPermission;

public interface SysPermissionService {
	 /**
     * 获取指定角色所有权限
     */
    public List<SysPermission> listByRoleId(Integer roleId);
}
