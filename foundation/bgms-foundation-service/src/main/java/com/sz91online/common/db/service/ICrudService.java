package com.sz91online.common.db.service;

import java.util.List;

public interface ICrudService<T> extends IService {

	T createObj();
	
	/**
	 * 保存记录接口
	 * @param record 要保存业务对象
	 * @param username 操作者账号
	 * @return
	 */
    Long saveWithSession( T record, String username);
    
    /**
     * 更新接口，会更新所有数据库字段，以ID为查询标记
     * @param record
     * @param username
     * @return
     */
    Integer updateWithSession( T record, String username);
    
    /**
     * 更新接口，只更新不为空的字段，以ID为查询标记
     * @param record
     * @param username
     * @return
     */
    Integer updateByPrimaryKeySelective( T record, String username);

    /**
     * 根据主键查询
     * @param primaryKey
     * @return
     */
    T findByPrimaryKey(Long primaryKey);

    /**
     * 根据业务主键查询，专用于唯一业务主键查询，其他任何字段不要使用
     * @param codePropertyName 主键名称
     * @param codeValue 查询值
     */
    public T findByCode(String codePropertyName, String codeValue);
    
    /**
     * 根据对象查询，会把对象中所有不这空的属性为查询条件进行查询。
     * @param bean
     * @return
     */
    List<T> findBySelective(T bean);
    
    /**
     * 查询所有的对象
     * @return
     */
    List<T> findAll();
    
    /**
     * 根据业务主键查询唯一记录
     * @param bean
     * @return
     */
    T findOne(T bean);

    /**
     * 删除接口，会根据 item的主键删除
     * @param item 要删除的对象
     * @param username 操作者账号
     */
    void removeWithSession(Long id, String username);

    /**
     * 批量删除接口
     * @param items
     * @param username
     */
    void massRemoveWithSession(List<Long> ids, String username);
}
