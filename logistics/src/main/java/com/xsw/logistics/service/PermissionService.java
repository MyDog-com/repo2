package com.xsw.logistics.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xsw.logistics.pojo.Permission;
import com.xsw.logistics.pojo.PermissionExample;

public interface PermissionService {
	int deleteByPrimaryKey(Long permissionId);

    int insert(Permission record);

    int insertSelective(Permission record);

    List<Permission> selectByExample(PermissionExample example);

    Permission selectByPrimaryKey(Long permissionId);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    int selectChildrenCountByParentId(Long permissionId);
    List<String> selectPermissionExpressinoByPermissinoIds(@Param("permissionIds")List<Long> permissionIds);


}
