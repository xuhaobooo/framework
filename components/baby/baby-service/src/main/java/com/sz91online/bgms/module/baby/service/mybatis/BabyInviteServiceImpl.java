package com.sz91online.bgms.module.baby.service.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.baby.dao.BabyInviteMapper;
import com.sz91online.bgms.module.baby.dao.BabyInviteMapperExt;
import com.sz91online.bgms.module.baby.domain.BabyInvite;
import com.sz91online.bgms.module.baby.service.BabyInviteService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
public class BabyInviteServiceImpl extends DefaultSearchService<BabyInvite> implements BabyInviteService {

	@Autowired
	private BabyInviteMapper babyInviteMapper;

	@Autowired
	private BabyInviteMapperExt babyInviteMapperExt;

	@Override
	public ISearchableDAO getSearchMapper() {
		return babyInviteMapperExt;
	}

	@Override
	public ICrudGenericDAO<BabyInvite> getCrudMapper() {
		return babyInviteMapper;
	}

}
