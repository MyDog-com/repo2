package com.xsw.logistics.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsw.logistics.model.MsgObject;
import com.xsw.logistics.pojo.Permission;
import com.xsw.logistics.pojo.PermissionExample;
import com.xsw.logistics.pojo.Role;
import com.xsw.logistics.pojo.RoleExample;
import com.xsw.logistics.pojo.User;
import com.xsw.logistics.pojo.Permission;
import com.xsw.logistics.pojo.PermissionExample.Criteria;
import com.xsw.logistics.service.PermissionService;

@Controller
@RequestMapping("permission")
public class PermissionController {
	@Autowired
	private PermissionService permissionService;
	private List<Permission> selectByExample;

	@RequestMapping("/permissionPage")
	public String adminPage(Model m) {
		return "permissionPage";
	}
	
	
	@RequestMapping("getAllPermissions")
	@ResponseBody
	public List<Permission> getAllPermissions() {
		PermissionExample example = new PermissionExample();
		List<Permission> permissions = permissionService.selectByExample(example);
		return permissions;
		
	}
	@RequestMapping("list")
	@ResponseBody
	public PageInfo<Permission> list(@RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") Integer pageSize, String keyword) {
		PageHelper.startPage(pageNum, pageSize);
		PermissionExample example = new PermissionExample();
		if (StringUtils.isNotBlank(keyword)) {
				
		}

		List<Permission> permissions = permissionService.selectByExample(example);
		PageInfo<Permission> pageInfo = new PageInfo<>(permissions);
		return pageInfo;
	}

	// 删除
	@RequestMapping("delete")
	@ResponseBody
	public MsgObject delete(Long permissionId) {

		MsgObject mo = new MsgObject(0, "删除数据失败，请联系管理员！");

		int count = permissionService.selectChildrenCountByParentId(permissionId);
		if (count == 0) {
			int row = permissionService.deleteByPrimaryKey(permissionId);
			if (row == 1) {
				mo = new MsgObject(1, "删除数据成功!");
			}
		} else {
			mo = new MsgObject(2, "该权限还有子权限不能直接删除！");
		}

		return mo;
	}

	// 编辑
	@RequestMapping("edit")
	public String edit(Model m, Long permissionId) {
		/*
		 * 修改操作，根据id查询出权限对象，共享过去，用于数据回显
		 */
		if (permissionId != null) {
			Permission permission = permissionService.selectByPrimaryKey(permissionId);
			m.addAttribute("permission", permission);

			
		}
		// 查询所有的权限，作为父权限以供选择
		
			PermissionExample example = new PermissionExample();
			List<Permission> permissions = permissionService.selectByExample(example);
			m.addAttribute("parents", permissions);
		
		return "permissionEdit";
	}

	// 新增权限
	@RequestMapping("insert")
	@ResponseBody
	public MsgObject insert(Permission permission) {
		MsgObject mo = new MsgObject(0, "新增数据失败，请联系管理员！");
		int row = permissionService.insert(permission);
		if (row == 1) {
			mo = new MsgObject(1, "新增数据成功!");
		}

		return mo;

	}

	// 管理员修改
	@RequestMapping("update")
	@ResponseBody
	public MsgObject update(Permission permission) {
		MsgObject mo = new MsgObject(0, "修改数据失败，请联系管理员！");
		int row = permissionService.updateByPrimaryKeySelective(permission);
		if (row == 1) {
			mo = new MsgObject(1, "修改数据成功!");
		}

		return mo;

	}
}
