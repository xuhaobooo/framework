package com.sz91online.common.db.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import com.sz91online.common.exceptions.EBusinessException;

public abstract class DefaultCrudService<T> implements ICrudService<T> {

	public abstract ICrudGenericDAO<T> getCrudMapper();

	protected Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Override
	public T findByPrimaryKey(Long primaryKey) {
		return getCrudMapper().selectByPrimaryKey(primaryKey);
	}

	@Override
	public List<T> findBySelective(T bean) {
		return getCrudMapper().selectByBean(bean);
	}

	@Override
	public T findOne(T bean) {
		List<T> resultList = getCrudMapper().selectByBean(bean);
		if (resultList == null || resultList.isEmpty()) {
			return null;
		} else if (resultList.size() > 1) {
			throw EBusinessException.DB_SELECTONE_IS_MANY;
		} else {
			return resultList.get(0);
		}
	}

	@Override
	public List<T> findAll() {
		// LOG.info("query all user from DB");
		return getCrudMapper().selectAll();
	}

	@Override
	@Transactional
	public Long saveWithSession(T record, String username) {
		if (!StringUtils.isBlank(username)) {
			try {
				PropertyUtils.setProperty(record, "createUserCode", username);
				/* PropertyUtils.setProperty(record, "updateUser", username); */
			} catch (Exception e) {
			}
		}

		try {
			PropertyUtils.setProperty(record, "createTime", new GregorianCalendar().getTime());
			/*
			 * PropertyUtils.setProperty(record, "lastUpdateTime", new
			 * GregorianCalendar().getTime());
			 */
		} catch (Exception e) {
		}

		try {
			getCrudMapper().insertAndReturnKey(record);
			return (Long) PropertyUtils.getProperty(record, "id");
		} catch (DuplicateKeyException dx) {
			throw EBusinessException.DB_RECORD_EXIST;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@Transactional
	public Integer updateWithSession(T record, String username) {
		try {
			PropertyUtils.setProperty(record, "lastUpdateTime", new GregorianCalendar().getTime());
			/* PropertyUtils.setProperty(record, "updateUser", username); */
		} catch (Exception e) {
		}
		try {
			return getCrudMapper().updateByPrimaryKey(record);
		} catch (DuplicateKeyException dx) {
			throw EBusinessException.DB_RECORD_EXIST;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@Transactional
	public Integer updateByPrimaryKeySelective(T record, String username) {
		try {
			PropertyUtils.setProperty(record, "lastUpdateTime", new GregorianCalendar().getTime());
			/* PropertyUtils.setProperty(record, "updateUser", username); */
		} catch (Exception e) {
		}
		try {
			return getCrudMapper().updateByPrimaryKeySelective(record);
		} catch (DuplicateKeyException dx) {
			throw EBusinessException.DB_RECORD_EXIST;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@Transactional
	public final void removeWithSession(T item, String username) {
		massRemoveWithSession(Arrays.asList(item), username);
	}

	@Override
	@Transactional
	public void massRemoveWithSession(List<T> items, String username) {
		List<T> primaryKeys = new ArrayList<>(items.size());
		for (T item : items) {
			try {
				T primaryKey = (T) PropertyUtils.getProperty(item, "id");
				primaryKeys.add(primaryKey);
			} catch (Exception e) {
				throw new EBusinessException(e);
			}
		}
		getCrudMapper().removeKeysWithSession(primaryKeys);
	}

	@Override
	public T createObj() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		try {
			return (T) ((Class) params[0]).newInstance();
		} catch (Exception e) {
			LOG.error("create obj error!");
			throw EBusinessException.CREATE_OBJ_ERROR;
		}
	}

}
