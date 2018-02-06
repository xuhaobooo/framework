package com.sz91online.bgms.module.baby.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sz91online.bgms.module.baby.domain.SimpleBabyEvaluation;
import com.sz91online.common.db.service.ISearchableDAO;

public interface BabyEvaluationMapperExt extends ISearchableDAO {

	List<SimpleBabyEvaluation> findMine(@Param(value = "user_code") String userCode,
			@Param(value = "start_time") Date startTime, @Param(value = "end_time") Date endTime);
}