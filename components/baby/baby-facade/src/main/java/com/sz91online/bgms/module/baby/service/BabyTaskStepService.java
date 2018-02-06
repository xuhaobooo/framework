package com.sz91online.bgms.module.baby.service;

import java.util.List;

import com.sz91online.bgms.module.baby.domain.BabyTaskStep;
import com.sz91online.bgms.module.baby.domain.SimpleBabyTaskStep;
import com.sz91online.common.db.service.IDefaultService;

public interface BabyTaskStepService extends IDefaultService<BabyTaskStep>{

	List<SimpleBabyTaskStep> findStepsByRequireCode(String requireCode);


}
