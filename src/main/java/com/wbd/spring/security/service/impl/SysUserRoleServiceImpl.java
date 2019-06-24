package com.wbd.spring.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wbd.spring.security.dto.SysUserRole;
import com.wbd.spring.security.mapper.SysUserRoleMapper;
import com.wbd.spring.security.service.SysUserRoleService;

@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

	@Autowired
	private SysUserRoleMapper userRoleMapper;

	@Override
	public List<SysUserRole> listByUserId(Integer userId) {
		return userRoleMapper.listByUserId(userId);
	}

}
