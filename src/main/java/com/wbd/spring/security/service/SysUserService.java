package com.wbd.spring.security.service;

import com.wbd.spring.security.dto.SysUser;

public interface SysUserService {
	  public SysUser selectById(Integer id);
	  
	  public SysUser selectByName(String name);
}
