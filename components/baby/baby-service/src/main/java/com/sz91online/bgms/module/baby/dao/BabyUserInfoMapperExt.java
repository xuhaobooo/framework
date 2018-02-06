package com.sz91online.bgms.module.baby.dao;

import org.apache.ibatis.annotations.Param;

import com.sz91online.bgms.module.baby.domain.BabyUserInfo;
import com.sz91online.bgms.module.baby.domain.SimpleBabyUserInfo;
import com.sz91online.common.db.service.ISearchableDAO;

public interface BabyUserInfoMapperExt extends ISearchableDAO {

	SimpleBabyUserInfo findRequireApplySucc(@Param(value = "require_code") String requireCode);
	
}