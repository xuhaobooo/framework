package com.sz91online.bgms.controller.security;

import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.sz91online.bgms.foundation.web.exception.ELoginException;
import com.sz91online.common.constant.JsonConstant;

public class ExtendFormAuthenticationFilter extends FormAuthenticationFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		/*
		 * Subject currentUser = SecurityUtils.getSubject(); if (currentUser != null &&
		 * currentUser.isAuthenticated()) { return true; }
		 * 
		 * if (isLoginRequest(request, response)) { if (isLoginSubmission(request,
		 * response)) { return executeLogin(request, response); } else { return true; }
		 * } else {
		 */
		/*if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {// 不是ajax请求
			saveRequestAndRedirectToLogin(request, response);
		} else {*/
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");
			((HttpServletResponse) response).addHeader(JsonConstant.RESULT_CODE, ELoginException.USER_NEED_LOGIN.getResultCode());
			((HttpServletResponse) response).addHeader(JsonConstant.RESULT_MSG,  URLEncoder.encode(ELoginException.USER_NEED_LOGIN.getResultMsg(), "UTF-8"));
			PrintWriter out = response.getWriter();
			out.println("{\"success\":true,\"msg\":\"请先登录！\"}");
			out.flush();
			out.close();
		//}
		return false;
		// }
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		/*
		 * if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest
		 * .getHeader("X-Requested-With"))) {// 不是ajax请求 issueSuccessRedirect(request,
		 * response); } else {
		 */
		httpServletResponse.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = httpServletResponse.getWriter();
		out.println("{\"success\":true,\"msg\":\"登入成功\"}");
		out.flush();
		out.close();
		// }
		return false;
	}

	/*@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {// 不是ajax请求
			setFailureAttribute(request, e);
			return true;
		}
		try {
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			httpServletResponse.setCharacterEncoding("UTF-8");
			httpServletResponse.setContentType("application/json;charset=UTF-8");
			PrintWriter out = httpServletResponse.getWriter();
			String message = e.getClass().getSimpleName();
			
			if ("IncorrectCredentialsException".equals(message)) {
				httpServletResponse.addHeader(JsonConstant.RESULT_CODE, "11100001");
				httpServletResponse.addHeader(JsonConstant.RESULT_MSG, URLEncoder.encode("密码错误", "UTF-8"));
				out.println("{\"success\":false,\"message\":\"密码错误\"}");
			} else if ("UnknownAccountException".equals(message)) {
				httpServletResponse.addHeader(JsonConstant.RESULT_CODE, "11100002");
				httpServletResponse.addHeader(JsonConstant.RESULT_MSG, URLEncoder.encode("账号不存在", "UTF-8"));
				out.println("{\"success\":false,\"message\":\"账号不存在\"}");
			} else if ("LockedAccountException".equals(message)) {
				httpServletResponse.addHeader(JsonConstant.RESULT_CODE, "11100003");
				httpServletResponse.addHeader(JsonConstant.RESULT_MSG, URLEncoder.encode("账号被锁定", "UTF-8"));
				out.println("{\"success\":false,\"message\":\"账号被锁定\"}");
			} else {
				httpServletResponse.addHeader(JsonConstant.RESULT_CODE, "11100004");
				httpServletResponse.addHeader(JsonConstant.RESULT_MSG, URLEncoder.encode("登录失败", "UTF-8"));
				out.println("{\"success\":false,\"message\":\"未知错误\"}");
			}
			out.flush();
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}*/
}
