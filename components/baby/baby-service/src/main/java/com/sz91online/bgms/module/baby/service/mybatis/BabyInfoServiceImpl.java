package com.sz91online.bgms.module.baby.service.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sz91online.bgms.module.baby.dao.BabyInfoMapper;
import com.sz91online.bgms.module.baby.dao.BabyInfoMapperExt;
import com.sz91online.bgms.module.baby.domain.BabyInfo;
import com.sz91online.bgms.module.baby.service.BabyInfoService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
public class BabyInfoServiceImpl extends DefaultSearchService<BabyInfo> implements BabyInfoService {

	@Autowired
	private BabyInfoMapper babyInfoMapper;

	@Autowired
	private BabyInfoMapperExt babyInfoMapperExt;

	@Override
	public ISearchableDAO getSearchMapper() {
		return babyInfoMapperExt;
	}

	@Override
	public ICrudGenericDAO<BabyInfo> getCrudMapper() {
		return babyInfoMapper;
	}

	@Override
	@Transactional
	public void addBabyToUser(String userCode, String babyCode) {
		babyInfoMapperExt.addBabyToUser(userCode, babyCode);
	}

	@Override
	public List<BabyInfo> findByUserCode(String userCode) {
		return babyInfoMapperExt.findByUserCode(userCode);
	}

	@Override
	@Transactional
	public void removeBabyFromUser(String userCode, String babyCode) {
		babyInfoMapperExt.removeBabyFromUser(userCode, babyCode);
	}

}
