package com.xsw.logistics.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsw.logistics.pojo.User;
import com.xsw.logistics.pojo.UserExample;
import com.xsw.logistics.pojo.UserExample.Criteria;
import com.xsw.logistics.service.UserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class UserServiceTest {
@Autowired
private UserService userService;
	@Test
	public void testDeleteByPrimaryKey() {
		
	}

	@Test
	public void testInsert() {
	}

	@Test
	public void testInsertSelective() {
	}

	@Test
	public void testSelectByExample() {
		int pageNum=1;
		int pageSize = 10;
		String keyWords = "g";
		PageHelper.startPage(pageNum, pageSize);
		UserExample example = new UserExample();
		Criteria criteria1 = example.createCriteria();
		criteria1.andUsernameLike("%"+keyWords+"%");
		
		Criteria criteria2 = example.createCriteria();
		criteria2.andRealnameLike("%"+keyWords+"%");
		example.or(criteria2);
		
		
		List<User> users = userService.selectByExample(example);
		PageInfo<User> pageInfo = new PageInfo<>(users);
		System.out.println(pageInfo.getTotal());
		System.out.println(pageInfo.getList());
		System.out.println(pageInfo.getPages());
	}

	@Test
	public void testSelectByPrimaryKey() {
		User user = userService.selectByPrimaryKey(1L);
		
	}

	@Test
	public void testUpdateByPrimaryKeySelective() {
	}

	@Test
	public void testUpdateByPrimaryKey() {
	}

}

