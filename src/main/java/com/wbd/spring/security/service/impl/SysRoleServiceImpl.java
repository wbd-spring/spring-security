package com.wbd.spring.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wbd.spring.security.dto.SysRole;
import com.wbd.spring.security.mapper.SysRoleMapper;
import com.wbd.spring.security.service.SysRoleService;

@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	private SysRoleMapper roleMapper;
	
	
	@Override
	public SysRole selectById(Integer id) {
		return roleMapper.selectById(id);
	}

}
