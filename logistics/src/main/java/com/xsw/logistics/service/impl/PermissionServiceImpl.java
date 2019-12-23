package com.xsw.logistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xsw.logistics.mapper.PermissionMapper;
import com.xsw.logistics.pojo.Permission;
import com.xsw.logistics.pojo.PermissionExample;
import com.xsw.logistics.service.PermissionService;
@Service
public class PermissionServiceImpl implements PermissionService {

		@Autowired
		private PermissionMapper permissionMapper;
	
	@Override
	public int deleteByPrimaryKey(Long permissionId) {
		
		return permissionMapper.deleteByPrimaryKey(permissionId);
	}

	@Override
	public int insert(Permission record) {
		return permissionMapper.insert(record);
	}

	@Override
	public int insertSelective(Permission record) {
		return permissionMapper.insertSelective(record);
	}

	@Override
	public List<Permission> selectByExample(PermissionExample example) {
		return permissionMapper.selectByExample(example);
	}

	@Override
	public Permission selectByPrimaryKey(Long permissionId) {
		return permissionMapper.selectByPrimaryKey(permissionId);
	}

	@Override
	public int updateByPrimaryKeySelective(Permission record) {
		return permissionMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Permission record) {
		return permissionMapper.updateByPrimaryKey(record);
	}

	@Override
	public int selectChildrenCountByParentId(Long permissionId) {
		// TODO Auto-generated method stub
		return permissionMapper.selectChildrenCountByParentId(permissionId);
	}

	@Override
	public List<String> selectPermissionExpressinoByPermissinoIds(List<Long> permissionIds) {
		// TODO Auto-generated method stub
		return permissionMapper.selectPermissionExpressinoByPermissinoIds(permissionIds);
	}

}
