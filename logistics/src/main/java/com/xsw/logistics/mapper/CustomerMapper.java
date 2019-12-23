package com.xsw.logistics.mapper;

import com.xsw.logistics.pojo.Customer;
import com.xsw.logistics.pojo.CustomerExample;

import java.util.List;

public interface CustomerMapper {
    int insert(Customer record);

    int insertSelective(Customer record);

    List<Customer> selectByExample(CustomerExample example);
    
    Customer selectByPrimaryKey(Long customerId);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);
    
    int deleteByPrimaryKey(Long customerId);
}