package com.sz91online.bgms.module.baby.service;

import java.util.Date;
import java.util.List;

import com.sz91online.bgms.module.baby.domain.BabyEvaluation;
import com.sz91online.bgms.module.baby.domain.SimpleBabyEvaluation;
import com.sz91online.common.db.service.IDefaultService;

public interface BabyEvaluationService extends IDefaultService<BabyEvaluation> {

	List<SimpleBabyEvaluation> findMine(String userCode, Date startTime, Date endTime);

}
