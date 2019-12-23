package com.xsw.logistics;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.xsw.logistics.pojo.User;

public class MyFormAuthentictionFilter extends FormAuthenticationFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
			HttpServletRequest req = (HttpServletRequest) request;
		/* 获取验证码 */
			String verifyCode = req.getParameter("verifyCode");
		HttpSession session = req.getSession();
		String rand = (String) session.getAttribute("rand");
		if (StringUtils.isNotBlank(verifyCode) && StringUtils.isNotBlank(rand)) {
			if (!verifyCode.equalsIgnoreCase(rand)) {
				req.setAttribute("erroryMsg", "臭傻逼，验证码错误！");
				req.getRequestDispatcher("/login.jsp").forward(req, response);
				return false;
			}
		}
			
		return super.onAccessDenied(request, response);
	}
	
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		Session session = subject.getSession(false);
		if (session!=null) {
			session.removeAttribute(WebUtils.SAVED_REQUEST_KEY);
		}
		
		return super.onLoginSuccess(token, subject, request, response);
	}
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		Subject subject = getSubject(request, response);
		Session session = subject.getSession();
		if (!subject.isAuthenticated()&&subject.isRemembered()) {
			User principal = (User) subject.getPrincipal();
			session.setAttribute("user", principal);
		}
		return subject.isAuthenticated()||subject.isRemembered();
	}
}
