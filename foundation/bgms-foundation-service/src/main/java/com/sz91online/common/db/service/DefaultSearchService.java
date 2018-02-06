package com.sz91online.common.db.service;

public abstract class DefaultSearchService<T> extends DefaultCrudService<T>
		implements IDefaultService<T> {

	public abstract ISearchableDAO getSearchMapper();

}
