package com.xsw.logistics.mapper;

import com.xsw.logistics.pojo.Role;
import com.xsw.logistics.pojo.RoleExample;
import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

	/* 通过角色id查出对应的所有权限 */
    String selectPermissionIdsByRoledId(Long roleId);
}