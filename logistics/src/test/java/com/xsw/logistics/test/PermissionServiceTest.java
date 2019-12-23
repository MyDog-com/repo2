package com.xsw.logistics.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xsw.logistics.pojo.Permission;
import com.xsw.logistics.pojo.PermissionExample;
import com.xsw.logistics.service.PermissionService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class PermissionServiceTest {

	@Autowired
	private PermissionService permissionService;
	@Test
	public void testSelectByExample() {
		PermissionExample example = new PermissionExample();
		List<Permission> permissions = permissionService.selectByExample(example);
	}
	
	@Test
	public void selectPermissionExpressinoByPermissinoIds() throws Exception {
			List<Long> permissionIds = Arrays.asList(1L,12L,11L,8L);
			List<String> expressinos = permissionService.selectPermissionExpressinoByPermissinoIds(permissionIds);
			
			System.out.println(expressinos);
	}

}
