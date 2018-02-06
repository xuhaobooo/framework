package com.sz91online.bgms.module.baby.service.mybatis;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.baby.dao.BabyEvaluationMapper;
import com.sz91online.bgms.module.baby.dao.BabyEvaluationMapperExt;
import com.sz91online.bgms.module.baby.domain.BabyEvaluation;
import com.sz91online.bgms.module.baby.domain.BabyRequireItem;
import com.sz91online.bgms.module.baby.domain.SimpleBabyEvaluation;
import com.sz91online.bgms.module.baby.service.BabyEvaluationService;
import com.sz91online.bgms.module.baby.service.BabyRequireItemService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
public class BabyEvaluationServiceImpl extends DefaultSearchService<BabyEvaluation> implements BabyEvaluationService {

	@Autowired
	private BabyEvaluationMapper babyEvaluationMapper;

	@Autowired
	private BabyEvaluationMapperExt babyEvaluationMapperExt;

	@Autowired
	private BabyRequireItemService itemservice;

	@Override
	public ISearchableDAO getSearchMapper() {
		return babyEvaluationMapperExt;
	}

	@Override
	public ICrudGenericDAO<BabyEvaluation> getCrudMapper() {
		return babyEvaluationMapper;
	}

	@Override
	public List<SimpleBabyEvaluation> findMine(String userCode, Date startTime, Date endTime) {
		List<SimpleBabyEvaluation> resultBeanList = babyEvaluationMapperExt.findMine(userCode, startTime, endTime);
		for (Iterator<SimpleBabyEvaluation> iterator = resultBeanList.iterator(); iterator.hasNext();) {
			SimpleBabyEvaluation simpleBabyTask = (SimpleBabyEvaluation) iterator.next();

			BabyRequireItem itemQueryBean = new BabyRequireItem();
			itemQueryBean.setRequireCode(simpleBabyTask.getRequireCode());
			List<BabyRequireItem> items = itemservice.findBySelective(itemQueryBean);
			String itemCon = "";
			for (Iterator iterator2 = items.iterator(); iterator2.hasNext();) {
				BabyRequireItem babyRequireItem = (BabyRequireItem) iterator2.next();
				itemCon = itemCon + babyRequireItem.getItemName();
				if (iterator2.hasNext()) {
					itemCon = itemCon + ",";
				}
			}
			simpleBabyTask.setRequireItems(itemCon);

		}
		return resultBeanList;
	}

}
