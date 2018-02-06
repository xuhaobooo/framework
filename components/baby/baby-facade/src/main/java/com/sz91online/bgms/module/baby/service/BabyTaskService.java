package com.sz91online.bgms.module.baby.service;

import java.util.Date;
import java.util.List;

import com.sz91online.bgms.module.baby.domain.BabyTask;
import com.sz91online.bgms.module.baby.domain.SimpleBabyTask;
import com.sz91online.common.db.service.IDefaultService;

public interface BabyTaskService extends IDefaultService<BabyTask> {

	List<SimpleBabyTask> listTasks(String userCode, String status, Date startTime, Date endTime);

	List<SimpleBabyTask> listDoneTasks(String userCode);

	SimpleBabyTask findSimpleTask(String requireCode);

	void cancleTask(String userCode, String requireCode);
	

}
