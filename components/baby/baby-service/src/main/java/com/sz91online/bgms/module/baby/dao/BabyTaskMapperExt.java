package com.sz91online.bgms.module.baby.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sz91online.bgms.module.baby.domain.SimpleBabyTask;
import com.sz91online.common.db.service.ISearchableDAO;

public interface BabyTaskMapperExt extends ISearchableDAO {

	List<SimpleBabyTask> listTasks(@Param(value = "user_code") String userCode,
			@Param(value = "status") String status, @Param(value = "start_time") Date startTime,
			@Param(value = "end_time") Date endTime);

	List<SimpleBabyTask> listDoneTasks(String userCode);

	SimpleBabyTask findSimpleTaskByRequireCode(String requireCode);

	SimpleBabyTask findSimpleTaskByTaskCode(String requireCode);

	void setTaskStatus(@Param(value = "require_code") String requireCode,@Param(value = "status") String value);
}