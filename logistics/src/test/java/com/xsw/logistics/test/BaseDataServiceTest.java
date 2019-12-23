package com.xsw.logistics.test;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xsw.logistics.pojo.BaseData;
import com.xsw.logistics.pojo.BaseDataExample;
import com.xsw.logistics.pojo.BaseDataExample.Criteria;
import com.xsw.logistics.service.BaseDataService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class BaseDataServiceTest {
@Autowired
	BaseDataService baseDataService ;
	@Test
	public void testSelectByExample() {
		BaseDataExample example = new BaseDataExample();

		String keyword = "大";

		if (StringUtils.isNotBlank(keyword)) {

			Criteria criteria1 = example.createCriteria();
			criteria1.andBaseNameLike("%" + keyword + "%");

			Criteria criteria2 = example.createCriteria();
			criteria2.andBaseDescLike("%" + keyword + "%");

			example.or(criteria2);

		}
		List<BaseData> selectByExample = baseDataService.selectByExample(example);
	}
	

}
