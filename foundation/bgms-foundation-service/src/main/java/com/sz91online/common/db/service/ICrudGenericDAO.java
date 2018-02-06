package com.sz91online.common.db.service;

import java.util.List;

public interface ICrudGenericDAO<T> {

	/**
	 * @param record
	 */
	void insert(T record);

	/**
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(T record);

	/**
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(T record);

	/**
	 * @param id
	 * @return
	 */
	T selectByPrimaryKey(Long primaryKey);
	
	List<T> selectByBean(T bean);

	/**
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long primaryKey);

	/**
	 * 
	 * @param value
	 * @return
	 */
	Long insertAndReturnKey(T value);

	/**
	 * 
	 * @param keys
	 */
	void removeKeysWithSession(List keys);
	
	List<T> selectAll();

}
