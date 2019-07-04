package com.wbd.spring.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wbd.spring.security.dto.SysPermission;
import com.wbd.spring.security.mapper.SysPermissionMapper;
import com.wbd.spring.security.service.SysPermissionService;
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

	@Autowired
	private SysPermissionMapper  permissionMapper;
	@Override
	public List<SysPermission> listByRoleId(Integer roleId) {
	
		return permissionMapper.listByRoleId(roleId);
	}

}
