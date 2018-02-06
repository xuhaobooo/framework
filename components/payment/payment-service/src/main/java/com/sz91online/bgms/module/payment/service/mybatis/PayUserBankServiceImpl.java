package com.sz91online.bgms.module.payment.service.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sz91online.bgms.module.payment.dao.PayUserBankMapper;
import com.sz91online.bgms.module.payment.dao.PayUserBankMapperExt;
import com.sz91online.bgms.module.payment.domain.PayUserBank;
import com.sz91online.bgms.module.payment.service.PayUserBankService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
@EnableCaching
public class PayUserBankServiceImpl extends DefaultSearchService<PayUserBank> implements PayUserBankService  {

	@Autowired
	private PayUserBankMapper payUserBankMapper;
	
	@Autowired
	private PayUserBankMapperExt payUserBankMapperExt;
	
	@Override
	public ISearchableDAO getSearchMapper() {
		return payUserBankMapperExt;
	}

	@Override
	public ICrudGenericDAO<PayUserBank> getCrudMapper() {
		return payUserBankMapper;
	}

	@Override
	@Transactional
	public void saveBankInfo(PayUserBank userBank) {
		PayUserBank queryBean = new PayUserBank();
		queryBean.setAccountNo(userBank.getAccountNo());
		queryBean.setUserCode(userBank.getUserCode());
		PayUserBank resultBean = this.findOne(queryBean);
		if(resultBean == null) {
			this.saveWithSession(userBank, userBank.getUserCode());
		}else {
			if(resultBean.getAccountName().equals(userBank.getAccountName()) && resultBean.getBank().equals(userBank.getBank())) {
				return;
			}else {
				resultBean.setAccountName(userBank.getAccountName());
				resultBean.setBank(userBank.getBank());
				this.updateByPrimaryKeySelective(resultBean, resultBean.getUserCode());
			}
		}
	}

}
