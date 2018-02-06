package com.sz91online.bgms.module.baby.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sz91online.bgms.module.baby.domain.SimpleBabyTaskStep;
import com.sz91online.common.db.service.ISearchableDAO;

public interface BabyTaskStepMapperExt extends ISearchableDAO {

	List<SimpleBabyTaskStep> findStepsByRequireCode(@Param(value="require_code") String requireCode);
}