package com.sz91online.bgms.module.baby.service;

import com.sz91online.bgms.module.baby.domain.BabyApply;
import com.sz91online.common.db.service.IDefaultService;

public interface BabyApplyService extends IDefaultService<BabyApply>{

	void updateFail(String requireCode, Long applyId);


}
