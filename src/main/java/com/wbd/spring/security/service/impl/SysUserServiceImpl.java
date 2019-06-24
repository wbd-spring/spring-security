package com.wbd.spring.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wbd.spring.security.dto.SysUser;
import com.wbd.spring.security.mapper.SysUserMapper;
import com.wbd.spring.security.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper userMapper;

	@Override
	public SysUser selectById(Integer id) {

		return userMapper.selectById(id);
	}

	@Override
	public SysUser selectByName(String name) {
		return userMapper.selectByName(name);
	}

}
