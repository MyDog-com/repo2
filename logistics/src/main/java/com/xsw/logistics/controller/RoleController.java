package com.xsw.logistics.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsw.logistics.model.MsgObject;
import com.xsw.logistics.pojo.Role;
import com.xsw.logistics.pojo.RoleExample;
import com.xsw.logistics.pojo.RoleExample.Criteria;
import com.xsw.logistics.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	


	@RequestMapping("/rolePage")
	@RequiresPermissions("role:rolePage")
	public String rolePage(Model m) {
		return "rolePage";
	}

	//查出所有的管理员信息
	@RequestMapping("list")
	@RequiresPermissions("role:list")
	@ResponseBody
	public PageInfo<Role> list(@RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") Integer pageSize, String keyword) {
		PageHelper.startPage(pageNum, pageSize);
		RoleExample example = new RoleExample();
		if (StringUtils.isNotBlank(keyword)) {
			Criteria criteria = example.createCriteria();
			criteria.andRolenameLike("%"+keyword+"%");
			
			Criteria criteria2 = example.createCriteria();
			criteria2.andRemarkLike("%"+keyword+"%");
			example.or(criteria2);
			
		}

		List<Role> roles = roleService.selectByExample(example);
		PageInfo<Role> pageInfo = new PageInfo<>(roles);
		return pageInfo;
	}
	//删除
	@RequestMapping("delete")
	@ResponseBody
	public MsgObject delete(Long roleId) {
		MsgObject mo = new MsgObject(0, "删除数据失败，请联系管理员！");
		int row = roleService.deleteByPrimaryKey(roleId);
		if (row==1) {
			mo= new MsgObject(1, "删除数据成功!");
		}
		return mo;
	}
	//编辑
	@RequestMapping("edit")
	public String edit(Model m,Long roleId) {
		if (roleId!=null) {
			Role role = roleService.selectByPrimaryKey(roleId);
			m.addAttribute("role", role);
		}
		RoleExample example = new RoleExample();
		List<Role> roles = roleService.selectByExample(example);
		m.addAttribute("roles", roles);
		
		return "roleEdit";
	}
	//新增管理员
	@RequestMapping("insert")
	@ResponseBody
	public MsgObject insert(Role role) {
		MsgObject mo = new MsgObject(0, "新增数据失败，请联系管理员！");
		int row = roleService.insert(role);
		if (row==1) {
			mo = new MsgObject(1, "新增数据成功!");
		}
		
		return mo;
		
	}
	//验证用户名唯一性
	@RequestMapping("checkRolename")
	@ResponseBody
	public boolean checkRolename(String rolename) {
		RoleExample example = new RoleExample();
		Criteria criteria = example.createCriteria();
		criteria.andRolenameEqualTo(rolename);
		List<Role> roles = roleService.selectByExample(example);
		if (roles.size()>0) {
			return false;
		}
		return true;
	}
	//批量删除
	@RequestMapping("deleteBatches")
	public void deleteBatches(HttpServletRequest request) {
		String roles = request.getParameter("roles");
		System.out.println(roles);
	}
	//管理员修改
	@RequestMapping("update")
	@ResponseBody
	public MsgObject update(Role role) {
		MsgObject mo = new MsgObject(0, "修改数据失败，请联系管理员！");
		int row = roleService.updateByPrimaryKeySelective(role);
		if (row==1) {
			mo = new MsgObject(1, "修改数据成功!");
		}
		
		return mo;
		
	}
		
	
}


























