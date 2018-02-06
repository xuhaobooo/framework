package com.sz91online.bgms.module.baby.service.mybatis;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.baby.dao.BabyTaskMapper;
import com.sz91online.bgms.module.baby.dao.BabyTaskMapperExt;
import com.sz91online.bgms.module.baby.domain.BabyTask;
import com.sz91online.bgms.module.baby.domain.BabyTaskStep;
import com.sz91online.bgms.module.baby.domain.SimpleBabyTask;
import com.sz91online.bgms.module.baby.enums.BabyRequireStatusEnum;
import com.sz91online.bgms.module.baby.exceptions.EBabyException;
import com.sz91online.bgms.module.baby.service.BabyTaskService;
import com.sz91online.bgms.module.baby.service.BabyTaskStepService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
public class BabyTaskServiceImpl extends DefaultSearchService<BabyTask> implements BabyTaskService {

	@Autowired
	private BabyTaskMapper babyTaskMapper;

	@Autowired
	private BabyTaskMapperExt babyTaskMapperExt;

	@Autowired
	private BabyTaskStepService taskStepService;

	@Override
	public ISearchableDAO getSearchMapper() {
		return babyTaskMapperExt;
	}

	@Override
	public ICrudGenericDAO<BabyTask> getCrudMapper() {
		return babyTaskMapper;
	}

	@Override
	public List<SimpleBabyTask> listTasks(String userCode, String status, Date startTime, Date endTime) {

		List<SimpleBabyTask> resultList = babyTaskMapperExt.listTasks(userCode, status, startTime, endTime);

		for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
			SimpleBabyTask simpleBabyTask = (SimpleBabyTask) iterator.next();
			BabyTaskStep stepQueryBean = new BabyTaskStep();
			stepQueryBean.setTaskCode(simpleBabyTask.getTaskCode());
			List<BabyTaskStep> stepList = taskStepService.findBySelective(stepQueryBean);

			simpleBabyTask.setStepList(stepList);
		}

		return resultList;
	}

	@Override
	public List<SimpleBabyTask> listDoneTasks(String userCode) {
		List<SimpleBabyTask> resultList = babyTaskMapperExt.listDoneTasks(userCode);

		for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
			SimpleBabyTask simpleBabyTask = (SimpleBabyTask) iterator.next();
			BabyTaskStep stepQueryBean = new BabyTaskStep();
			stepQueryBean.setTaskCode(simpleBabyTask.getTaskCode());
			List<BabyTaskStep> stepList = taskStepService.findBySelective(stepQueryBean);

			simpleBabyTask.setStepList(stepList);
		}

		return resultList;
	}

	@Override
	public SimpleBabyTask findSimpleTask(String requireCode) {
		SimpleBabyTask resultBean = babyTaskMapperExt.findSimpleTaskByRequireCode(requireCode);
		
		if (resultBean == null) {
			resultBean = babyTaskMapperExt.findSimpleTaskByTaskCode(requireCode);
		}
		
		if (resultBean == null) {
			throw EBabyException.BABY_TASK_NOT_EXIST;
		}

		BabyTaskStep stepQueryBean = new BabyTaskStep();
		stepQueryBean.setTaskCode(resultBean.getTaskCode());
		List<BabyTaskStep> stepList = taskStepService.findBySelective(stepQueryBean);

		resultBean.setStepList(stepList);

		return resultBean;
	}

	@Override
	public void cancleTask(String userCode, String requireCode) {
		SimpleBabyTask resultBean = babyTaskMapperExt.findSimpleTaskByRequireCode(requireCode);
		if (resultBean == null) {
			return;
		}
		babyTaskMapperExt.setTaskStatus(requireCode, BabyRequireStatusEnum.CANCEL.getValue());
	}

}
