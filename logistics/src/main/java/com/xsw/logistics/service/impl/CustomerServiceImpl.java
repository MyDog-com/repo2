package com.xsw.logistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xsw.logistics.mapper.CustomerMapper;
import com.xsw.logistics.pojo.Customer;
import com.xsw.logistics.pojo.CustomerExample;
import com.xsw.logistics.service.CustomerService;
@Service
public class CustomerServiceImpl implements CustomerService {

		@Autowired
		private CustomerMapper CustomerMapper;
	
	@Override
	public int deleteByPrimaryKey(Long CustomerId) {
		
		return CustomerMapper.deleteByPrimaryKey(CustomerId);
	}

	@Override
	public int insert(Customer record) {
		return CustomerMapper.insert(record);
	}

	@Override
	public int insertSelective(Customer record) {
		return CustomerMapper.insertSelective(record);
	}

	@Override
	public List<Customer> selectByExample(CustomerExample example) {
		return CustomerMapper.selectByExample(example);
	}

	@Override
	public Customer selectByPrimaryKey(Long CustomerId) {
		return CustomerMapper.selectByPrimaryKey(CustomerId);
	}

	@Override
	public int updateByPrimaryKeySelective(Customer record) {
		return CustomerMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Customer record) {
		return CustomerMapper.updateByPrimaryKey(record);
	}

}
