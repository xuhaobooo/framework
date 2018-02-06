package com.sz91online.bgms.foundation.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sz91online.bgms.foundation.web.session.SessionUtil;
import com.sz91online.common.db.service.ICrudService;
import com.sz91online.common.exceptions.EBusinessException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Component
public abstract class BaseController<T> {

	/*
	 * @ExceptionHandler(Exception.class)
	 * 
	 * @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR) public ModelAndView
	 * handleException(Exception ex, HttpServletRequest request) { return new
	 * ModelAndView().addObject("error", "错误信息"); }
	 */

	public abstract ICrudService<T> getService();

	/**
	 * 无条件查询
	 * 
	 * @param property
	 * @param value
	 */
	@ApiOperation(value = "查询所有记录", httpMethod = "GET", notes = "对记录太多的表请不要调用。可分页查询.分页请求和返回参数都使用http header传输<br/>"
			+ "返回的分页数据： <br/> x-total-count：总记录数<br/> x-page-num：当前显示第几页<br/> x-page-size:每页的记录数<br/> x-page-count：总共多少页<br/> x-cur-page-record-size:当前页有多少条记录")
	@RequestMapping(value = "current", method = RequestMethod.GET)
	public @ResponseBody List<T> getAllEntity(
			@RequestHeader(value = "Page_Num", required = false) @ApiParam(name = "Page_Num", value = "当前页数", required = false) Integer pageNum,
			@RequestHeader(value = "Page_Size", required = false) @ApiParam(name = "Page_Size", value = "每页的数量", required = false) Integer pageSize,
			HttpServletResponse response) {

		if (pageNum != null && pageSize != null) {
			if(pageSize > 50) {
				pageSize = 50;
			}
			PageHelper.startPage(pageNum, pageSize);
		}
		List<T> beanList = getService().findAll();

		if (pageNum != null && pageSize != null) {
			PageInfo<T> pageUser = new PageInfo<T>(beanList);

			response.setHeader("x-total-count", String.valueOf(pageUser.getTotal())); // 总记录数
			response.setHeader("x-page-num", String.valueOf(pageUser.getPageNum())); // 当前显示第几页
			response.setHeader("x-page-size", String.valueOf(pageUser.getPageSize())); // 每页的记录数
			response.setHeader("x-page-count", String.valueOf(pageUser.getPages())); // 总共多少页
			response.setHeader("x-cur-page-record-size", String.valueOf(pageUser.getSize())); // 当前页有多少条记录
		}

		return beanList;
	}

	/**
	 * 根据ID查询
	 * 
	 * @param property
	 * @param value
	 */
	@ApiOperation(value = "通过ID查询记录", httpMethod = "GET", notes = "此ID为数据自增长主健，非业务主健。业务主键一般以xxxCode命名")
	@RequestMapping(value = "getById/{id}", method = RequestMethod.GET)
	public @ResponseBody T getEntityById(
			@PathVariable @ApiParam(name = "id", value = "记录数据库主键", required = true) String id) {
		return getService().findByPrimaryKey(Long.parseLong(id));
	}

	/**
	 * 一个参数的查询
	 * 
	 * @param property
	 * @param value
	 */
	@ApiOperation(value = "通过一个属性查询对象", httpMethod = "GET", notes = "可分页查询.分页请求和返回参数都使用http header传输<br/>"
			+ "返回的分页数据： <br/> x-total-count：总记录数<br/> x-page-num：当前显示第几页<br/> x-page-size:每页的记录数<br/> x-page-count：总共多少页<br/> x-cur-page-record-size:当前页有多少条记录")
	@RequestMapping(value = "current/{property}/{value}", method = RequestMethod.GET)
	public @ResponseBody List<T> getCurEntity(
			@PathVariable @ApiParam(name = "property", value = "属性名称", required = true) String property,
			@PathVariable @ApiParam(name = "value", value = "属性值", required = true) Object value,
			@RequestHeader(value = "Page_Num", required = false) @ApiParam(name = "Page_Num", value = "当前页数", required = false) Integer pageNum,
			@RequestHeader(value = "Page_Size", required = false) @ApiParam(name = "Page_Size", value = "每页的数量", required = false) Integer pageSize,
			HttpServletResponse response) {
		T t = getService().createObj();
		try {
			if (PropertyUtils.getPropertyType(t, property) == null) {
				throw EBusinessException.MIS_PARAMETER_ERROR;
			}
			BeanUtils.setProperty(t, property, value);
		} catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
			throw EBusinessException.MIS_PARAMETER_ERROR;
		}

		if (pageNum != null && pageSize != null) {
			PageHelper.startPage(pageNum, pageSize);
		}

		List<T> beanList = getService().findBySelective(t);

		if (pageNum != null && pageSize != null) {
			PageInfo<T> pageUser = new PageInfo<T>(beanList);

			response.setHeader("x-total-count", String.valueOf(pageUser.getTotal())); // 总记录数

			response.setHeader("x-page-num", String.valueOf(pageUser.getPageNum())); // 当前显示第几页
			response.setHeader("x-page-size", String.valueOf(pageUser.getPageSize())); // 每页的记录数

			response.setHeader("x-page-count", String.valueOf(pageUser.getPages())); // 总共多少页
			response.setHeader("x-cur-page-record-size", String.valueOf(pageUser.getSize())); // 当前页有多少条记录
		}
		return beanList;
	}

	/**
	 * 两个参数的查询
	 * 
	 * @param property1
	 * @param value1
	 * @param property2
	 * @param value2
	 */
	@ApiOperation(value = "通过两个属性查询对象", httpMethod = "GET", notes = "可分页查询.分页请求和返回参数都使用http header传输<br/>"
			+ "返回的分页数据： <br/> x-total-count：总记录数<br/> x-page-num：当前显示第几页<br/> x-page-size:每页的记录数<br/> x-page-count：总共多少页<br/> x-cur-page-record-size:当前页有多少条记录")
	@RequestMapping(value = "current/{property1}/{value1}/{property2}/{value2}", method = RequestMethod.GET)
	public @ResponseBody List<T> getCurEntity1(
			@PathVariable @ApiParam(name = "property1", value = "属性1名称", required = true) String property1,
			@PathVariable @ApiParam(name = "value1", value = "属性1值", required = true) Object value1,
			@PathVariable @ApiParam(name = "property2", value = "属性2名称", required = true) String property2,
			@PathVariable @ApiParam(name = "value2", value = "属性2值", required = true) Object value2,
			@RequestHeader(value = "Page_Num", required = false) @ApiParam(name = "Page_Num", value = "当前页数", required = false) Integer pageNum,
			@RequestHeader(value = "Page_Size", required = false) @ApiParam(name = "Page_Size", value = "每页的数量", required = false) Integer pageSize,
			HttpServletResponse response) throws IllegalAccessException, InstantiationException {

		T t = getService().createObj();

		try {
			if (PropertyUtils.getPropertyType(t, property1) == null
					|| PropertyUtils.getPropertyType(t, property2) == null) {
				throw EBusinessException.MIS_PARAMETER_ERROR;
			}
			BeanUtils.setProperty(t, property1, value1);
			BeanUtils.setProperty(t, property2, value2);
		} catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
			throw EBusinessException.MIS_PARAMETER_ERROR;
		}
		if (pageNum != null && pageSize != null) {
			PageHelper.startPage(pageNum, pageSize);
		}
		List<T> beanList = getService().findBySelective(t);

		if (pageNum != null && pageSize != null) {
			PageInfo<T> pageUser = new PageInfo<T>(beanList);

			response.setHeader("x-total-count", String.valueOf(pageUser.getTotal())); // 总记录数

			response.setHeader("x-page-num", String.valueOf(pageUser.getPageNum())); // 当前显示第几页
			response.setHeader("x-page-size", String.valueOf(pageUser.getPageSize())); // 每页的记录数

			response.setHeader("x-page-count", String.valueOf(pageUser.getPages())); // 总共多少页
			response.setHeader("x-cur-page-record-size", String.valueOf(pageUser.getSize())); // 当前页有多少条记录
		}
		return beanList;
	}

	/**
	 * 新增
	 * 
	 * @param t
	 * @return
	 */
	@ApiOperation(value = "添加新记录", httpMethod = "POST", notes = "返回执行保存记录后的记录，包含数据库主键id")
	@RequestMapping(method = RequestMethod.POST, value = "current")
	public @ResponseBody T saveCurEntity(@RequestBody @ApiParam(name = "业务对象", value = "传入json格式", required = true) T t,
			HttpServletRequest request) {
		getService().saveWithSession(t, SessionUtil.getSessionUser().getCode());
		return t;
	}

	/**
	 * 全量修改
	 * 
	 * @param t
	 * @return
	 */
	@ApiOperation(value = "全量修改记录", httpMethod = "PATCH", notes = "数据库主健id必须有，此操作为全量更新，未上传的字段会被替换为空值。如果只更新某些字段，请使用。返回执行保存记录后的记录")
	@RequestMapping(method = RequestMethod.PATCH, value = "current/{id}")
	public @ResponseBody T modifyCurEntity(
			@PathVariable @ApiParam(name = "id", value = "操作记录的数据库主健", required = true) String id,
			@RequestBody @ApiParam(name = "业务对象", value = "传入json格式", required = true) T t) {

		if (id == null) {
			throw EBusinessException.MIS_PARAMETER_ERROR;
		} else {
			try {
				Object propId = BeanUtils.getProperty(t, "id");
				if (propId == null) {
					BeanUtils.setProperty(t, "id", id);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			T queryBean = getService().findByPrimaryKey(Long.parseLong(id));
			if (queryBean == null) {
				throw EBusinessException.DB_RECORD_NOT_EXIST;
			}

			int count = getService().updateWithSession(t, SessionUtil.getSessionUser().getCode());

			if (count == 0) {
				throw EBusinessException.DB_UPDATE_RESULT_0;
			}

			return t;
		}
	}

	/**
	 * 增量修改
	 * 
	 * @param t
	 * @return
	 */
	@ApiOperation(value = "增量更新记录", httpMethod = "PATCH", notes = "数据库主健id必须有，此操作为增量更新，未上传的字段不会被修改。返回执行保存记录后的记录")
	@RequestMapping(method = RequestMethod.PATCH, value = "update/{id}")
	public @ResponseBody T updateCurEntity(
			@PathVariable @ApiParam(name = "id", value = "操作记录的数据库主健", required = true) String id,
			@RequestBody @ApiParam(name = "业务对象", value = "传入json格式", required = true) T t) {

		if (id == null) {
			throw EBusinessException.MIS_PARAMETER_ERROR;
		} else {
			try {
				Object propId = BeanUtils.getProperty(t, "id");
				if (propId == null) {
					BeanUtils.setProperty(t, "id", id);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			T queryBean = getService().findByPrimaryKey(Long.parseLong(id));
			if (queryBean == null) {
				throw EBusinessException.DB_RECORD_NOT_EXIST;
			}

			int count = getService().updateByPrimaryKeySelective(t, SessionUtil.getSessionUser().getCode());

			if (count == 0) {
				throw EBusinessException.DB_UPDATE_RESULT_0;
			}

			return t;
		}
	}

	/**
	 * 删除
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return
	 */
	@ApiOperation(value = "删除数据库记录", httpMethod = "DELETE", notes = "只删除本身记录，不会有级联的删除")
	@RequestMapping(value = "current/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void removeCruEntity(
			@PathVariable @ApiParam(name = "id", value = "操作记录的数据库主健", required = true) String id)
			throws IllegalAccessException, InstantiationException {

		T t = getService().createObj();
		try {
			BeanUtils.setProperty(t, "id", id);
			getService().removeWithSession(t, SessionUtil.getSessionUser().getCode());
		} catch (InvocationTargetException | IllegalAccessException e) {
			throw EBusinessException.MIS_PARAMETER_ERROR;
		}
	}

	/**
	 * 删除
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return
	 */
	/*@ApiOperation(value = "current", httpMethod = "DELETE", notes = "删除多条数据库记录")
	@RequestMapping(value = "current", method = RequestMethod.DELETE)
	public @ResponseBody List<T> removeCruEntity(
			@ApiParam(name = "ids", value = "多条记录的数据库主健", required = true) String... ids)
			throws IllegalAccessException, InstantiationException {

		List<T> delIds = new ArrayList<T>();

		try {
			for (int i = 0; i < ids.length; i++) {
				T t = getService().createObj();
				BeanUtils.setProperty(t, "id", ids[i]);
				delIds.add(t);
			}

			if (delIds.size() == 0) {
				throw EBusinessException.MIS_PARAMETER_ERROR;
			}

			getService().massRemoveWithSession(delIds, SessionUtil.getSessionUser().getCode());
			return delIds;
		} catch (InvocationTargetException e) {
			throw new EBusinessException("-1", e.getMessage());
		}
	}*/

}
