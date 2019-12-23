package com.xsw.logistics.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xsw.logistics.pojo.User;
import com.xsw.logistics.pojo.UserExample;

public interface UserService {
	int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectUsersByRoleName(@Param("roleName")String roleName);

	int deleteByuserIds(List<Long> userIdList);
}
