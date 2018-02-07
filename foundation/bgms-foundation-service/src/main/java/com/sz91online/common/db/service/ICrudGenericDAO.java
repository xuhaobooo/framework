package com.sz91online.common.db.service;

import java.util.List;

public interface ICrudGenericDAO<T> {

	void insert(T record);

	int updateByPrimaryKey(T record);

	int updateByPrimaryKeySelective(T record);

	T selectByPrimaryKey(Long primaryKey);
	
	List<T> selectByBean(T bean);

	int deleteByPrimaryKey(Long primaryKey);

	Long insertAndReturnKey(T value);

	void removeKeysWithSession(List<Long> keys);
	
	List<T> selectAll();

}
