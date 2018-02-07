package com.sz91online.bgms.foundation.web.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.pagehelper.PageHelper;
import com.sz91online.bgms.foundation.web.interceptor.PageAndSortInterceptor;

public class PageAndSortUtil {
	
	private static Logger logger = LoggerFactory.getLogger(PageAndSortUtil.class);

	private static ThreadLocal<List<?>> listThreadLocal = new ThreadLocal<>();
	private static ThreadLocal<Pagination> pageThreadLocal = new ThreadLocal<>();

	public static void setPageData(List<?> data) {
		listThreadLocal.set(data);
	}

	public static List<?> getPageData() {
		return listThreadLocal.get();
	}
	
	public static void setPageInfo(Pagination pageInfo) {
		pageThreadLocal.set(pageInfo);
	}
	
	public static void startPagaQuery() {
		Pagination pageInfo = pageThreadLocal.get();
		if(pageInfo != null) {
			if(pageInfo.getOrderBy()!=null) {
				PageHelper.startPage(pageInfo.getCurPage(), pageInfo.getPageSize(),pageInfo.getOrderBy());
			}else {
				PageHelper.startPage(pageInfo.getCurPage(), pageInfo.getPageSize());
			}
		}else {
			logger.error("调用分页时没有分页信息！");
		}
		
	}
	
	public static void startPagaQuery(Pagination pageInfo) {
		if(pageInfo != null) {
			PageHelper.startPage(pageInfo.getCurPage(), pageInfo.getPageSize());
		}else {
			logger.error("调用分页时没有分页信息！");
		}
		
	}
}
