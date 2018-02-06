package com.sz91online.bgms.module.baby.service;

import com.sz91online.bgms.module.baby.domain.BabyUserInfo;
import com.sz91online.bgms.module.baby.domain.SimpleBabyUserInfo;
import com.sz91online.common.db.service.IDefaultService;

public interface BabyUserInfoService extends IDefaultService<BabyUserInfo> {

	BabyUserInfo addUserInfo(SimpleBabyUserInfo userInfo);

	SimpleBabyUserInfo findRequireApplySucc(String requireCode);

}
