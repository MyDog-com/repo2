package com.xsw.logistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xsw.logistics.mapper.RoleMapper;
import com.xsw.logistics.pojo.Role;
import com.xsw.logistics.pojo.RoleExample;
import com.xsw.logistics.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {

		@Autowired
		private RoleMapper roleMapper;
	
	@Override
	public int deleteByPrimaryKey(Long roleId) {
		
		return roleMapper.deleteByPrimaryKey(roleId);
	}

	@Override
	public int insert(Role record) {
		return roleMapper.insert(record);
	}

	@Override
	public int insertSelective(Role record) {
		return roleMapper.insertSelective(record);
	}

	@Override
	public List<Role> selectByExample(RoleExample example) {
		return roleMapper.selectByExample(example);
	}

	@Override
	public Role selectByPrimaryKey(Long roleId) {
		return roleMapper.selectByPrimaryKey(roleId);
	}

	@Override
	public int updateByPrimaryKeySelective(Role record) {
		return roleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Role record) {
		return roleMapper.updateByPrimaryKey(record);
	}

	@Override
	public String selectPermissionIdsByRoledId(Long roleId) {
		// TODO Auto-generated method stub
		return roleMapper.selectPermissionIdsByRoledId(roleId);
	}

	

}
