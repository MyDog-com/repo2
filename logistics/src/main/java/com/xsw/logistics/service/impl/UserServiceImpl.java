package com.xsw.logistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xsw.logistics.mapper.UserMapper;
import com.xsw.logistics.pojo.User;
import com.xsw.logistics.pojo.UserExample;
import com.xsw.logistics.service.UserService;
@Service
public class UserServiceImpl implements UserService {

		@Autowired
		private UserMapper userMapper;
	
	@Override
	public int deleteByPrimaryKey(Long userId) {
		
		return userMapper.deleteByPrimaryKey(userId);
	}

	@Override
	public int insert(User record) {
		return userMapper.insert(record);
	}

	@Override
	public int insertSelective(User record) {
		return userMapper.insertSelective(record);
	}

	@Override
	public List<User> selectByExample(UserExample example) {
		return userMapper.selectByExample(example);
	}

	@Override
	public User selectByPrimaryKey(Long userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {
		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(User record) {
		return userMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<User> selectUsersByRoleName(String roleName) {
		return userMapper.selectUsersByRoleName(roleName);
	}

	@Override
	public int deleteByuserIds(List<Long> userIdList) {
		// TODO Auto-generated method stub
		return userMapper.deleteByuserIds(userIdList);
	}


}
