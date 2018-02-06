package com.sz91online.bgms.foundation.web.session;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sz91online.bgms.foundation.web.exception.ELoginException;
import com.sz91online.common.constant.Constants;

public class SessionUtil {
	public static void setSessionUser(UserSessionInfo userSessionInfo){
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(true);
		session.setAttribute(Constants.SessionUser,userSessionInfo);
	}


	public static UserSessionInfo getSessionUser(){
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(true);
		Object object = session.getAttribute(Constants.SessionUser);
		if(object!=null){
			return (UserSessionInfo)object;
		}else{
			//todo 未登录，所有写死用户，加入登录后修改
			throw ELoginException.USER_NEED_LOGIN;
		}
		//return null;
	}
	/**
	 * 删除session
	 */
	public static void removeSession(){
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(true);
		session.removeAttribute(Constants.SessionUser);
	}
}
