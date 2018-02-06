package com.sz91online.bgms.module.baby.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sz91online.bgms.module.baby.domain.BabyRequire;
import com.sz91online.bgms.module.baby.domain.SimpleBabyRequire;
import com.sz91online.bgms.module.baby.enums.BabyRequireStatusEnum;
import com.sz91online.common.db.service.ISearchableDAO;

public interface BabyRequireMapperExt extends ISearchableDAO {

	List<SimpleBabyRequire> getMyRequire(@Param(value = "user_code") String code,
			@Param(value = "status") String status, @Param(value = "start_time") Date startTime,
			@Param(value = "end_time") Date endTime);

	List<SimpleBabyRequire> listRequire(@Param(value = "start_time") Date startTime,
			@Param(value = "end_time") Date endTime, @Param(value = "pos_x") Double posX,
			@Param(value = "pos_y") Double posY);

	void setRequireStatus(@Param(value = "require_code") String requireCode, @Param(value = "status") String cancel);

	SimpleBabyRequire findSimpleRequireByCode(@Param(value = "require_code") String requireCode);
}