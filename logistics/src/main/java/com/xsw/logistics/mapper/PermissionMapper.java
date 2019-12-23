package com.xsw.logistics.mapper;

import com.xsw.logistics.pojo.Permission;
import com.xsw.logistics.pojo.PermissionExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PermissionMapper {
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