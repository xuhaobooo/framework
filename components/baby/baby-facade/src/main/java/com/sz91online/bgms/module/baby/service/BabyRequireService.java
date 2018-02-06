package com.sz91online.bgms.module.baby.service;

import java.util.Date;
import java.util.List;

import com.sz91online.bgms.module.baby.domain.BabyApply;
import com.sz91online.bgms.module.baby.domain.BabyRequire;
import com.sz91online.bgms.module.baby.domain.BabyRequireItemDict;
import com.sz91online.bgms.module.baby.domain.SimpleBabyApply;
import com.sz91online.bgms.module.baby.domain.SimpleBabyRequire;
import com.sz91online.bgms.module.baby.domain.SimpleBabyTask;
import com.sz91online.common.db.service.IDefaultService;

public interface BabyRequireService extends IDefaultService<BabyRequire> {

	void publish(SimpleBabyRequire babyRequire, String code);

	List<SimpleBabyRequire> listMy(String code, String status, Date startTime, Date endTime);

	SimpleBabyRequire findSingle(String requireCode);

	List<SimpleBabyApply> findApply(String requireCode);

	void select(Long applyId);

	SimpleBabyTask findTask(String requireCode);

	void apply(String requireCode, String userCode);

	void arrive(String taskCode, String userCode);

	void provideFinish(String taskCode, String code);

	void customerFinish(String taskCode, String code);

	List<BabyRequireItemDict> getAllService();

	List<SimpleBabyRequire> listRequire(String userCode, Date startTime, Date endTime);

	List<SimpleBabyTask> listTasks(String userCode, String status, Date startTime, Date endTime);

	List<SimpleBabyTask> listDoneTasks(String userCode);

	void mockPay(String requireCode, String code);

	void cancelRequire(String userCode, String requireCode);

	void cancelTask(String code, String taskCode);

}
