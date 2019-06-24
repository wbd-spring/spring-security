package com.wbd.spring.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.wbd.spring.security.dto.SysUser;

@Mapper
public interface SysUserMapper {

	@Select("SELECT * FROM sys_user WHERE id = #{id}")
	SysUser selectById(Integer id);

	@Select("SELECT * FROM sys_user WHERE name = #{name}")
	SysUser selectByName(String name);
}
