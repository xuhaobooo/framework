package com.sz91online.bgms.module.user.service.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.user.dao.CaptchaMapper;
import com.sz91online.bgms.module.user.dao.CaptchaMapperExt;
import com.sz91online.bgms.module.user.domain.Captcha;
import com.sz91online.bgms.module.user.service.CaptchaService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
public class CaptchaServiceImpl extends DefaultSearchService<Captcha> implements CaptchaService  {

	@Autowired
	private CaptchaMapper captchaMapper;
	
	@Autowired
	private CaptchaMapperExt captchaMapperExt;
	
	
	@Override
	public ISearchableDAO getSearchMapper() {
		return captchaMapperExt;
	}

	@Override
	public ICrudGenericDAO<Captcha> getCrudMapper() {
		return captchaMapper;
	}

}
