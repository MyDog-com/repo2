package com.xsw.logistics.service;

import java.util.List;

import com.xsw.logistics.pojo.Customer;
import com.xsw.logistics.pojo.CustomerExample;

public interface CustomerService {
	int insert(Customer record);

    int insertSelective(Customer record);

    List<Customer> selectByExample(CustomerExample example);
    
    Customer selectByPrimaryKey(Long customerId);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);
    
    int deleteByPrimaryKey(Long customerId);
}
