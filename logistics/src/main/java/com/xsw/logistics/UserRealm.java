package com.xsw.logistics;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.xsw.logistics.pojo.User;
import com.xsw.logistics.pojo.UserExample;
import com.xsw.logistics.pojo.UserExample.Criteria;
import com.xsw.logistics.service.PermissionService;
import com.xsw.logistics.service.RoleService;
import com.xsw.logistics.service.UserService;

public class UserRealm extends AuthorizingRealm{

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("我是授权方法！*******************************************************");
		 User user = (User) principals.getPrimaryPrincipal();
		 Long roleId = user.getRoleId();
		 String permissionIdsStr = roleService.selectPermissionIdsByRoledId(roleId);
		 List<Long> permissionIds = new ArrayList<>();
		 String[] arr = permissionIdsStr.split(",");
		 for (String permissionId : arr) {
			permissionIds.add(Long.valueOf(permissionId));
		}
		/* 获取权限表达式 */
		List<String> expressions = permissionService.selectPermissionExpressinoByPermissinoIds(permissionIds);
		/* 将权限表达式封装到Shiro框架种 */
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addStringPermissions(expressions);
		 
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("我是认证方法");
		String principal = (String) token.getPrincipal();
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(principal);
		List<User> users = userService.selectByExample(example);
		if (users.size()==0) {
			return null;
		}
		User user = users.get(0);
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt, this.getName());
		return authenticationInfo;
	}

}
