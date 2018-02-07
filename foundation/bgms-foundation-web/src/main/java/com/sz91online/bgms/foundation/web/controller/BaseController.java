package com.sz91online.bgms.foundation.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sz91online.bgms.foundation.web.session.SessionUtil;
import com.sz91online.bgms.foundation.web.utils.PageAndSortUtil;
import com.sz91online.common.db.entity.RootBean;
import com.sz91online.common.db.service.ICrudService;
import com.sz91online.common.exceptions.EBusinessException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Component
public abstract class BaseController<T extends RootBean> {

	public abstract ICrudService<T> getService();

	@ApiOperation(value = "按条件查询记录", httpMethod = "GET", notes = "参数名为资源的属性字段名称，其他将被抛弃.<br/>"
			+ "分页请求参数----   currentPage：当前页数, pageSize：每页的数量<br/>" + "返回参数都使用http header传输,数据格式如下：<br/>"
			+ "x-total-count：总记录数<br/> x-current-page：当前显示第几页<br/> x-page-size:每页的记录数<br/> x-page-count：总共多少页<br/> x-cur-page-record-size:当前页有多少条记录")
	@RequestMapping(value = "/find/base", method = RequestMethod.GET)
	public @ResponseBody List<T> findByAttr(@ApiParam(name = "map", value = "请求参数的MAP集合，参数key必须是资源的属性") @RequestParam Map<String, String> map) {

		T queryBean = getService().createObj();
		this.mapProperty(queryBean, map);

		PageAndSortUtil.startPagaQuery();
		List<T> beanList = getService().findBySelective(queryBean);
		PageAndSortUtil.setPageData(beanList);
		return beanList;
	}

	private void mapProperty(T queryBean, Map<String, String> map) {
		try {
			BeanUtils.populate(queryBean, map);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
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
	 * 根据业务主键查询
	 * 
	 * @param property
	 * @param value
	 */
	@ApiOperation(value = "根据业务主键查询", httpMethod = "GET", notes = "业务主键一般以xxxCode命名,不是业务主键不要使用此接口")
	@RequestMapping(value = "getByCode/{codePeoperty}/{codeValue}", method = RequestMethod.GET)
	public @ResponseBody T getEntityByCode(
			@PathVariable @ApiParam(name = "codePeoperty", value = "业务主键属性名称", required = true) String codePeoperty,
			@PathVariable @ApiParam(name = "codeValue", value = "业务主键值", required = true) String codeValue) {
		return getService().findByCode(codePeoperty, codeValue);
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
	@RequestMapping(method = RequestMethod.PUT, value = "current")
	public @ResponseBody T modifyCurEntity(
			@RequestBody @ApiParam(name = "业务对象", value = "传入json格式", required = true) T t) {

		if (t.getId() == null) {
			throw EBusinessException.UPDATE_MISS_ID;
		}

		T queryBean = getService().findByPrimaryKey(t.getId());
		if (queryBean == null) {
			throw EBusinessException.DB_RECORD_NOT_EXIST;
		}

		int count = getService().updateWithSession(t, SessionUtil.getSessionUser().getCode());

		if (count == 0) {
			throw EBusinessException.DB_UPDATE_RESULT_0;
		}

		return t;
	}

	/**
	 * 增量修改
	 * 
	 * @param t
	 * @return
	 */
	@ApiOperation(value = "增量更新记录", httpMethod = "PATCH", notes = "数据库主健id必须有，此操作为增量更新，未上传的字段不会被修改。返回执行保存记录后的记录")
	@RequestMapping(method = RequestMethod.PATCH, value = "current")
	public @ResponseBody T updateCurEntity(
			@RequestBody @ApiParam(name = "业务对象", value = "传入json格式", required = true) T t) {

		if (t.getId() == null) {
			throw EBusinessException.UPDATE_MISS_ID;
		}

		T queryBean = getService().findByPrimaryKey(t.getId());
		if (queryBean == null) {
			throw EBusinessException.DB_RECORD_NOT_EXIST;
		}

		int count = getService().updateByPrimaryKeySelective(t, SessionUtil.getSessionUser().getCode());

		if (count == 0) {
			throw EBusinessException.DB_UPDATE_RESULT_0;
		}

		return t;
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@ApiOperation(value = "删除数据库记录", httpMethod = "DELETE", notes = "只删除本身记录，不会有级联的删除.")
	@RequestMapping(value = "current/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void removeCruEntity(
			@PathVariable @ApiParam(name = "id", value = "操作记录的数据库主健", required = true) String id)
			throws IllegalAccessException, InstantiationException {

		getService().removeWithSession(Long.parseLong(id), SessionUtil.getSessionUser().getCode());
	}

	/**
	 * 批量删除，只有管理员可调用
	 * 
	 */
	@ApiOperation(value = "current", httpMethod = "DELETE", notes = "删除多条数据库记录,id以逗号相连接")
	@RequiresRoles("admin")
	@RequestMapping(value = "current", method = RequestMethod.DELETE)
	public @ResponseBody void removeMultiRecord(
			@ApiParam(name = "ids", value = "多条记录的数据库主健", required = true) String ids)
			throws IllegalAccessException, InstantiationException {

		String[] idsArray = ids.split(",");
		List<Long> idList = new ArrayList<>();
		for (int i = 0; i < idsArray.length; i++) {
			idList.add(Long.parseLong(idsArray[i]));
		}
		getService().massRemoveWithSession(idList, SessionUtil.getSessionUser().getCode());
	}

}
