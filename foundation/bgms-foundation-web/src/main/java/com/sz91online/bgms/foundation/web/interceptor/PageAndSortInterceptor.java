package com.sz91online.bgms.foundation.web.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sz91online.bgms.foundation.web.utils.PageAndSortUtil;
import com.sz91online.bgms.foundation.web.utils.Pagination;

public class PageAndSortInterceptor implements HandlerInterceptor {

	private static Logger logger = LoggerFactory.getLogger(PageAndSortInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		int curPageInt = 1;
		int pageSizeInt = 10;
		try {
			
			String currentStr = request.getParameter("current_page");
			if (currentStr != null) {
				curPageInt = Integer.parseInt(currentStr);
			}
			
			String pageSizeStr = request.getParameter("page_size");
			if (pageSizeStr != null) {
				pageSizeInt = Integer.parseInt(pageSizeStr);
			}

			if (pageSizeInt > 50) {
				pageSizeInt = 50;
			}
			
		} catch (Exception e) {
			curPageInt = 1;
			pageSizeInt = 10;
			logger.error("分页参数不正确！");
		}
		
		String orderByStr = request.getParameter("orderby_field");
		String orderDes = request.getParameter("order");
		
		Pagination page = new Pagination();
		page.setCurPage(curPageInt);
		page.setPageSize(pageSizeInt);
		
		
		if(orderByStr!=null) {
			if("desc".equalsIgnoreCase(orderDes) || "asc".equalsIgnoreCase(orderDes)) {
				page.setOrderBy(orderByStr + " " + orderDes);
			}else {
				page.setOrderBy(orderByStr);
			}
		}
		
		PageAndSortUtil.setPageInfo(page);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		List<?> data = PageAndSortUtil.getPageData();
		if (data != null) {
			PageInfo<?> pageUser = new PageInfo<>(data);
			response.setHeader("x-total-count", String.valueOf(pageUser.getTotal())); // 总记录数
			response.setHeader("x-current-page", String.valueOf(pageUser.getPageNum())); // 当前显示第几页
			response.setHeader("x-page-size", String.valueOf(pageUser.getPageSize())); // 每页的记录数
			response.setHeader("x-page-count", String.valueOf(pageUser.getPages())); // 总共多少页
			response.setHeader("x-cur-page-record-size", String.valueOf(pageUser.getSize())); // 当前页有多少条记录
		}

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.debug("afterCompletion");

	}

}
