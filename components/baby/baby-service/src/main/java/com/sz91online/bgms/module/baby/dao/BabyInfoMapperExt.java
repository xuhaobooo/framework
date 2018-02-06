package com.sz91online.bgms.module.baby.dao;

import java.util.List;

import com.sz91online.bgms.module.baby.domain.BabyInfo;
import com.sz91online.common.db.service.ISearchableDAO;

public interface BabyInfoMapperExt extends ISearchableDAO {

	void addBabyToUser(String userCode, String babyCode);

	List<BabyInfo> findByUserCode(String userCode);

	void removeBabyFromUser(String userCode, String babyCode);
}