package com.xsw.logistics.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsw.logistics.model.MsgObject;
import com.xsw.logistics.pojo.BaseData;
import com.xsw.logistics.pojo.Customer;
import com.xsw.logistics.pojo.CustomerExample;
import com.xsw.logistics.pojo.CustomerExample.Criteria;
import com.xsw.logistics.pojo.User;
import com.xsw.logistics.pojo.UserExample;
import com.xsw.logistics.service.BaseDataService;
import com.xsw.logistics.service.CustomerService;
import com.xsw.logistics.service.UserService;
import com.xsw.logistics.utils.Constant;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
@Autowired
private UserService userService;

@Autowired
private BaseDataService baseDataService;
	@RequiresPermissions("customer:customerPage")
	@RequestMapping("/customerPage")
	public String customerPage(Model m) {
		return "customerPage";
	}

	//查出所有的管理员信息
	@RequiresPermissions("customer:list")
	@RequestMapping("list")
	@ResponseBody
	public PageInfo<Customer> list(@RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") Integer pageSize, String keyword) {
		PageHelper.startPage(pageNum, pageSize);
		CustomerExample example = new CustomerExample();
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		Long userId = user.getUserId();
		Criteria criteria = example.createCriteria();
		if (Constant.ROLE_SALESMAN.equals(user.getRolename())) {
			
			criteria.andUserIdEqualTo(userId);
		}
		
		
		if (StringUtils.isNotBlank(keyword)) {
			criteria.andCustomerNameLike("%"+keyword+"%");
		}

		List<Customer> customers = customerService.selectByExample(example);
		PageInfo<Customer> pageInfo = new PageInfo<>(customers);
		return pageInfo;
	}
	//删除
	@RequiresPermissions("customer:delete")
	@RequestMapping("delete")
	@ResponseBody
	public MsgObject delete(Long customerId) {
		MsgObject mo = new MsgObject(0, "删除数据失败，请联系管理员！");
		int row = customerService.deleteByPrimaryKey(customerId);
		if (row==1) {
			mo= new MsgObject(1, "删除数据成功!");
		}
		return mo;
	}
	//编辑
	
	@RequestMapping("edit")
	public String edit(Model m,Long customerId) {
		if (customerId!=null) {
			Customer customer = customerService.selectByPrimaryKey(customerId);
			m.addAttribute("customer", customer);
		}
		List<User> users = userService.selectUsersByRoleName(Constant.ROLE_SALESMAN);
		m.addAttribute("users", users); 
		
		List<BaseData> baseDatas = baseDataService.selectBaseDatasByParentName(Constant.BASIC_COMMON_INTERVAL);
		m.addAttribute("baseDatas",baseDatas);
		return "customerEdit";
	}
	//新增管理员
	@RequiresPermissions("customer:insert")
	@RequestMapping("insert")
	@ResponseBody
	public MsgObject insert(Customer customer) {
		MsgObject mo = new MsgObject(0, "新增数据失败，请联系管理员！");
		int row = customerService.insert(customer);
		if (row==1) {
			mo = new MsgObject(1, "新增数据成功!");
		}
		
		return mo;
		
	}
	//验证用户名唯一性
	@RequiresPermissions("customer:checkCustomername")
	@RequestMapping("checkCustomername")
	@ResponseBody
	public boolean checkCustomername(String customername) {
		CustomerExample example = new CustomerExample();
		Criteria criteria = example.createCriteria();
		criteria.andCustomerNameEqualTo(customername);
		List<Customer> customers = customerService.selectByExample(example);
		if (customers.size()>0) {
			return false;
		}
		return true;
	}
	//批量删除
	@RequestMapping("deleteBatches")
	public void deleteBatches(HttpServletRequest request) {
		String customers = request.getParameter("customers");
		System.out.println(customers);
	}
	//管理员修改
	@RequiresPermissions("customer:update")
	@RequestMapping("update")
	@ResponseBody
	public MsgObject update(Customer customer) {
		MsgObject mo = new MsgObject(0, "修改数据失败，请联系管理员！");
		int row = customerService.updateByPrimaryKeySelective(customer);
		if (row==1) {
			mo = new MsgObject(1, "修改数据成功!");
		}
		
		return mo;
		
	}
	
	@RequestMapping("login")
	public String login(HttpServletRequest request,Model m) throws ClassNotFoundException {
		String erroryMsg = (String) request.getAttribute("shiroLoginFailure");
		if (erroryMsg != null) {
			Class<?> clz = Class.forName(erroryMsg);
			if ("UnknownAccountException".equals(clz.getSimpleName())) {
				m.addAttribute("erroryMsg", "账号不不存在，臭傻逼！");
			} else if ("IncorrectCredentialsException".equals(clz.getSimpleName())) {
				m.addAttribute("erroryMsg", "密码错误，傻逼！");
			}
		}
		return "forward:/login.jsp";
	} 
	
	@RequestMapping("logout")
	public String logout() {
		return "redirect:/login.jsp";
	}
		
	
}


























