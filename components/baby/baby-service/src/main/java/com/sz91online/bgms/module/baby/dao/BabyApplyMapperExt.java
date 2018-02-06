package com.sz91online.bgms.module.baby.dao;

import com.sz91online.common.db.service.ISearchableDAO;

public interface BabyApplyMapperExt extends ISearchableDAO {

	void updateFail(String requireCode, Long applyId);
}