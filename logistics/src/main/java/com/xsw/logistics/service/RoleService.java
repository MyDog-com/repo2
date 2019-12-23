package com.xsw.logistics.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xsw.logistics.pojo.Role;
import com.xsw.logistics.pojo.RoleExample;

public interface RoleService {
	int deleteByPrimaryKey(Long roleId);

	int insert(Role record);

	int insertSelective(Role record);

	List<Role> selectByExample(RoleExample example);

	Role selectByPrimaryKey(Long roleId);

	int updateByPrimaryKeySelective(Role record);

	int updateByPrimaryKey(Role record);
    String selectPermissionIdsByRoledId(Long roleId);

}
