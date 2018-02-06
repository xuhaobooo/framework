package com.sz91online.bgms.module.baby.service.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.baby.dao.BabyTaskStepMapper;
import com.sz91online.bgms.module.baby.dao.BabyTaskStepMapperExt;
import com.sz91online.bgms.module.baby.domain.BabyTaskStep;
import com.sz91online.bgms.module.baby.domain.SimpleBabyTaskStep;
import com.sz91online.bgms.module.baby.service.BabyTaskStepService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
public class BabyTaskStepServiceImpl extends DefaultSearchService<BabyTaskStep> implements BabyTaskStepService {

	@Autowired
	private BabyTaskStepMapper babyTaskStepMapper;

	@Autowired
	private BabyTaskStepMapperExt babyTaskStepMapperExt;
	
	@Override
	public ISearchableDAO getSearchMapper() {
		return babyTaskStepMapperExt;
	}

	@Override
	public ICrudGenericDAO<BabyTaskStep> getCrudMapper() {
		return babyTaskStepMapper;
	}

	@Override
	public List<SimpleBabyTaskStep> findStepsByRequireCode(String requireCode) {
		return babyTaskStepMapperExt.findStepsByRequireCode(requireCode);
	}

}
