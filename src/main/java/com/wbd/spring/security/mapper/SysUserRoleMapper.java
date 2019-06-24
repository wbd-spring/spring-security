package com.wbd.spring.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.wbd.spring.security.dto.SysUserRole;

@Mapper
public interface SysUserRoleMapper {
	 @Select("SELECT * FROM sys_user_role WHERE user_id = #{userId}")
	    List<SysUserRole> listByUserId(Integer userId);
}
