package com.sz91online.bgms.module.baby.service.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sz91online.bgms.module.baby.dao.BabyApplyMapper;
import com.sz91online.bgms.module.baby.dao.BabyApplyMapperExt;
import com.sz91online.bgms.module.baby.domain.BabyApply;
import com.sz91online.bgms.module.baby.service.BabyApplyService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
public class BabyApplyServiceImpl extends DefaultSearchService<BabyApply> implements BabyApplyService {

	@Autowired
	private BabyApplyMapper babyApplyMapper;

	@Autowired
	private BabyApplyMapperExt babyApplyMapperExt;

	@Override
	public ISearchableDAO getSearchMapper() {
		return babyApplyMapperExt;
	}

	@Override
	public ICrudGenericDAO<BabyApply> getCrudMapper() {
		return babyApplyMapper;
	}

	@Override
	@Transactional
	public void updateFail(String requireCode, Long applyId) {
		babyApplyMapperExt.updateFail(requireCode, applyId);

	}

}
