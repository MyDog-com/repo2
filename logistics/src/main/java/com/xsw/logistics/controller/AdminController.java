package com.xsw.logistics.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
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
import com.xsw.logistics.pojo.User;
import com.xsw.logistics.pojo.UserExample;
import com.xsw.logistics.pojo.UserExample.Criteria;
import com.xsw.logistics.service.RoleService;
import com.xsw.logistics.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;

	@RequiresPermissions("admin:adminPage")
	@RequestMapping("/adminPage")
	public String adminPage(Model m) {
		System.out.println("我是真的帅啊!!!!");
		return "adminPage";
	}

	//查出所有的管理员信息
	@RequiresPermissions("admin:list")
	@RequestMapping("list")
	@ResponseBody
	public PageInfo<User> list(@RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") Integer pageSize, String keyword) {
		System.out.println("我是你爹谢谢!!!!");
		PageHelper.startPage(pageNum, pageSize);
		UserExample example = new UserExample();
		if (StringUtils.isNotBlank(keyword)) {
			Criteria criteria1 = example.createCriteria();
			criteria1.andUsernameLike("%" + keyword + "%");

			Criteria criteria2 = example.createCriteria();
			criteria2.andRealnameLike("%" + keyword + "%");
			example.or(criteria2);
		}

		List<User> users = userService.selectByExample(example);
		PageInfo<User> pageInfo = new PageInfo<>(users);
		return pageInfo;
	}
	//删除
	@RequiresPermissions("admin:delete")
	@RequestMapping("delete")
	@ResponseBody
	public MsgObject delete(Long userId) {
		MsgObject mo = new MsgObject(0, "删除数据失败，请联系管理员！");
		int row = userService.deleteByPrimaryKey(userId);
		if (row==1) {
			mo= new MsgObject(1, "删除数据成功!");
		}
		return mo;
	}
	//编辑
	
	@RequestMapping("edit")
	public String edit(Model m,Long userId) {
		if (userId!=null) {
			User user = userService.selectByPrimaryKey(userId);
			m.addAttribute("user", user);
		}
		RoleExample example = new RoleExample();
		List<Role> roles = roleService.selectByExample(example);
		m.addAttribute("roles", roles);
		
		return "adminEdit";
	}
	//新增管理员
	@RequiresPermissions("admin:insert")
	@RequestMapping("insert")
	@ResponseBody
	public MsgObject insert(User user) {
		String password = user.getPassword();
		String salt = UUID.randomUUID().toString().substring(0, 4);
		Md5Hash md5Hash = new Md5Hash(password, salt, 3);
		user.setPassword(md5Hash.toString());
		user.setSalt(salt);
		user.setCreateDate(new Date());
		MsgObject mo = new MsgObject(0, "新增数据失败，请联系管理员！");
		int row = userService.insert(user);
		if (row==1) {
			mo = new MsgObject(1, "新增数据成功!");
		}
		
		return mo;
		
	}
	//验证用户名唯一性
	@RequiresPermissions("admin:checkUsername")
	@RequestMapping("checkUsername")
	@ResponseBody
	public boolean checkUsername(String username) {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<User> users = userService.selectByExample(example);
		if (users.size()>0) {
			return false;
		}
		return true;
	}
	//批量删除
	@RequestMapping("deleteBatches")
	@ResponseBody
	public MsgObject deleteBatches(Long[] userIds) {
		MsgObject mo = new MsgObject(0, "删除数据失败，请联系管理员！");
		List<Long> userIdList = Arrays.asList(userIds);
		System.out.println(userIdList.toString());
		int row = userService.deleteByuserIds(userIdList);
		System.out.println("***********"+row);
		if (row>=1) {
			mo = new MsgObject(1, "删除数据成功!");
		}
		System.out.println(mo);
		
		return mo;		
	}
	//管理员修改
	@RequiresPermissions("admin:update")
	@RequestMapping("update")
	@ResponseBody
	public MsgObject update(User user) {
		
		String password = user.getPassword();
		String salt = UUID.randomUUID().toString().substring(0, 4);
		Md5Hash md5Hash = new Md5Hash(password, salt, 3);
		user.setPassword(md5Hash.toString());
		user.setSalt(salt);
		MsgObject mo = new MsgObject(0, "修改数据失败，请联系管理员！");
		int row = userService.updateByPrimaryKeySelective(user);
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


























