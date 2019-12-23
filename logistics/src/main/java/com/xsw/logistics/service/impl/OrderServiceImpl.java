package com.xsw.logistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xsw.logistics.mapper.OrderDetailMapper;
import com.xsw.logistics.mapper.OrderMapper;
import com.xsw.logistics.pojo.Order;
import com.xsw.logistics.pojo.OrderDetail;
import com.xsw.logistics.pojo.OrderDetailExample;
import com.xsw.logistics.pojo.OrderExample;
import com.xsw.logistics.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService {

		@Autowired
		private OrderMapper orderMapper;
		
		@Autowired
		private OrderDetailMapper orderDetailMapper;
	
	@Override
	public int deleteByPrimaryKey(Long orderId) {
		
		return orderMapper.deleteByPrimaryKey(orderId);
	}

	@Override
	public int insert(Order record) {
		int row = orderMapper.insert(record);
		if (row ==1) {
			List<OrderDetail> orderDetails = record.getOrderDetails();
			for (OrderDetail orderDetail : orderDetails) {
				orderDetail.setOrderId(record.getOrderId());
				int insert = orderDetailMapper.insert(orderDetail);
			}
		}
		
		
		
		return  row;
	}

	@Override
	public int insertSelective(Order record) {
		return orderMapper.insertSelective(record);
	}

	@Override
	public List<Order> selectByExample(OrderExample example) {
		return orderMapper.selectByExample(example);
	}

	@Override
	public Order selectByPrimaryKey(Long orderId) {
		return orderMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public int updateByPrimaryKeySelective(Order record) {
		return orderMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Order record) {
		return orderMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<OrderDetail> selectByExample(OrderDetailExample example) {
		// TODO Auto-generated method stub
		return orderDetailMapper.selectByExample(example);
	}

	

}
