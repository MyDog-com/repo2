package com.xsw.logistics.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsw.logistics.model.MsgObject;
import com.xsw.logistics.pojo.BaseData;
import com.xsw.logistics.pojo.Customer;
import com.xsw.logistics.pojo.CustomerExample;
import com.xsw.logistics.pojo.Order;
import com.xsw.logistics.pojo.OrderDetail;
import com.xsw.logistics.pojo.OrderDetailExample;
import com.xsw.logistics.pojo.OrderDetailExample.Criteria;
import com.xsw.logistics.pojo.OrderExample;
import com.xsw.logistics.pojo.User;
import com.xsw.logistics.service.BaseDataService;
import com.xsw.logistics.service.CustomerService;
import com.xsw.logistics.service.OrderService;
import com.xsw.logistics.service.UserService;
import com.xsw.logistics.utils.Constant;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private BaseDataService baseDataService;

	@Autowired
	private CustomerService customerService;

	@RequestMapping("/orderPage")
	@RequiresPermissions("order:orderPage")
	public String orderPage(Model m) {
		return "orderPage";
	}

	//查出所有的管理员信息
	@RequestMapping("list")
	@RequiresPermissions("order:list")
	@ResponseBody
	public PageInfo<Order> list(@RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") Integer pageSize, String keyword) {
		PageHelper.startPage(pageNum, pageSize);
		OrderExample example = new OrderExample();
		if (StringUtils.isNotBlank(keyword)) {
		
			
		}

		List<Order> orders = orderService.selectByExample(example);
		PageInfo<Order> pageInfo = new PageInfo<>(orders);
		return pageInfo;
	}
	//删除
	@RequestMapping("delete")
	@ResponseBody
	public MsgObject delete(Long orderId) {
		MsgObject mo = new MsgObject(0, "删除数据失败，请联系管理员！");
		int row = orderService.deleteByPrimaryKey(orderId);
		if (row==1) {
			mo= new MsgObject(1, "删除数据成功!");
		}
		return mo;
	}
	//编辑
	@RequestMapping("edit")
	public String edit(Model m,Long orderId) {
		
		List<User> users = userService.selectUsersByRoleName(Constant.ROLE_SALESMAN);
		
		
		CustomerExample customerExample = new CustomerExample();
		List<Customer> customers = customerService.selectByExample(customerExample);
		
		List<BaseData> intervals = baseDataService.selectBaseDatasByParentName(Constant.BASIC_COMMON_INTERVAL);
		List<BaseData> payments = baseDataService.selectBaseDatasByParentName(Constant.BASIC_PAYMENT_TYPE);
		List<BaseData> freights = baseDataService.selectBaseDatasByParentName(Constant.BASIC_FREIGHT_TYPE);
		List<BaseData> fetchTypes = baseDataService.selectBaseDatasByParentName(Constant.BASIC_FETCH_TYPE);
		List<BaseData> units = baseDataService.selectBaseDatasByParentName(Constant.BASIC_UNIT);
		m.addAttribute("users", users);
		m.addAttribute("customers", customers);
		m.addAttribute("intervals", intervals);
		m.addAttribute("payments", payments);
		m.addAttribute("freights", freights);
		m.addAttribute("fetchTypes", fetchTypes);
		m.addAttribute("units", units);
		if (orderId!=null) {
			Order order = orderService.selectByPrimaryKey(orderId);
			m.addAttribute("order", order);
			OrderDetailExample example = new OrderDetailExample();
			Criteria criteria = example.createCriteria();
			criteria.andOrderIdEqualTo(orderId);
			List<OrderDetail> orderDetails = orderService.selectByExample(example);
			m.addAttribute("orderDetails", orderDetails);
		}
		
		
		return "orderEdit";
	}
	//新增管理员
	@RequestMapping("insert")
	@ResponseBody
	public MsgObject insert(@RequestBody Order order) {
		MsgObject mo = new MsgObject(0, "新增数据失败，请联系管理员！");
		System.out.println(order);
		
		int row = orderService.insert(order);
		if (row==1) {
			mo = new MsgObject(1, "新增数据成功!");
		}
		
		return mo;
		
	}
	
	
	//管理员修改
	@RequestMapping("update")
	@ResponseBody
	public MsgObject update(Order order) {
		MsgObject mo = new MsgObject(0, "修改数据失败，请联系管理员！");
		int row = orderService.updateByPrimaryKeySelective(order);
		if (row==1) {
			mo = new MsgObject(1, "修改数据成功!");
		}
		
		return mo;
		
	}
	@RequestMapping("detail")
	@ResponseBody
	public List<OrderDetail> detail(Long orderId){
		OrderDetailExample example = new OrderDetailExample();
		Criteria criteria = example.createCriteria();
		criteria.andOrderIdEqualTo(orderId);
		
		List<OrderDetail> orderDetails = orderService.selectByExample(example);
		return orderDetails;
	}
		
	
}


























