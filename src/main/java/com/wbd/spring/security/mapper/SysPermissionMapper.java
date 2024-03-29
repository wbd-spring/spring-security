package com.wbd.spring.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.wbd.spring.security.dto.SysPermission;

@Mapper
public interface SysPermissionMapper {
	 @Select("SELECT * FROM sys_permission WHERE role_id=#{roleId}")
	    List<SysPermission> listByRoleId(Integer roleId);
}
