package com.sz91online.bgms.controller.exception;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.sz91online.bgms.foundation.web.exception.ELoginException;
import com.sz91online.common.constant.JsonConstant;
import com.sz91online.common.exceptions.EBusinessException;

/**
 * 不必在Controller中对异常进行处理，抛出即可，由此异常解析器统一控制。<br>
 * ajax请求（有@ResponseBody的Controller）发生错误，输出JSON。<br>
 * 页面请求（无@ResponseBody的Controller）发生错误，输出错误页面。<br>
 * 需要与AnnotationMethodHandlerAdapter使用同一个messageConverters<br>
 * Controller中需要有专门处理异常的方法。
 * 
 */
@ControllerAdvice
public class AnnotationHandlerMethodExceptionResolver extends ExceptionHandlerExceptionResolver {

	@Override
	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception exception) {
		String resultCode;
		String resultMsg;
		Logger logger = LoggerFactory.getLogger(AnnotationHandlerMethodExceptionResolver.class);
		logger.error(exception.getMessage());
		if (exception instanceof EBusinessException) {
			EBusinessException e = (EBusinessException) exception;
			resultCode = e.getResultCode();
			resultMsg = e.getResultMsg();
		} else if (exception instanceof IllegalArgumentException) {
			resultCode = JsonConstant.FAILURE_CODE;
			resultMsg = exception.getMessage();
		} else if (exception instanceof UnauthorizedException) {
			resultCode = ELoginException.USER_NO_AUTH.getResultCode();
			resultMsg = ELoginException.USER_NO_AUTH.getResultMsg();
		} else if (exception instanceof AuthenticationException) {
			String message = exception.getClass().getSimpleName();

			if ("IncorrectCredentialsException".equals(message)) {
				resultCode = ELoginException.NAME_PWD_ERROR.getResultCode();
				resultMsg = ELoginException.NAME_PWD_ERROR.getResultMsg();
			} else if ("UnknownAccountException".equals(message)) {
				resultCode = ELoginException.LOGIN_USER_NOT_EXIST_ERROR.getResultCode();
				resultMsg = ELoginException.LOGIN_USER_NOT_EXIST_ERROR.getResultMsg();
			} else if ("LockedAccountException".equals(message)) {
				resultCode = ELoginException.LOGIN_USER_BEEN_LOCKED.getResultCode();
				resultMsg = ELoginException.LOGIN_USER_BEEN_LOCKED.getResultMsg();
			} else {
				resultCode = ELoginException.LOGIN_FAIL_ERROR.getResultCode();
				resultMsg = ELoginException.LOGIN_FAIL_ERROR.getResultMsg();
			}
		} else {
			resultCode = JsonConstant.FAILURE_CODE;
			resultMsg = "系统错误，请联系管理员";
			exception.printStackTrace();
		}
		logger.error("error code =>>" + resultCode + "===" + "error message=>>" + resultMsg);

		response.setCharacterEncoding("UTF-8");
		// response.setStatus(521);
		response.setContentType("application/json; charset=utf-8");
		response.addHeader(JsonConstant.RESULT_CODE, resultCode);
		try {
			response.addHeader(JsonConstant.RESULT_MSG, URLEncoder.encode(resultMsg, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * PrintWriter writer = response.getWriter(); writer.write("{msg:" + resultMsg +
		 * "}"); writer.close(); return null;
		 */
		return new ModelAndView();

	}

}