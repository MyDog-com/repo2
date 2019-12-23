package com.xsw.logistics.service;

import java.util.List;

import com.xsw.logistics.pojo.Order;
import com.xsw.logistics.pojo.OrderDetail;
import com.xsw.logistics.pojo.OrderDetailExample;
import com.xsw.logistics.pojo.OrderExample;

public interface OrderService {
	int deleteByPrimaryKey(Long orderId);

	int insert(Order record);

	int insertSelective(Order record);

	List<Order> selectByExample(OrderExample example);

	Order selectByPrimaryKey(Long orderId);

	int updateByPrimaryKeySelective(Order record);

	int updateByPrimaryKey(Order record);
	
    List<OrderDetail> selectByExample(OrderDetailExample example);
	

}
