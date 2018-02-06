package com.sz91online.bgms.module.payment.service.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sz91online.bgms.module.payment.dao.PayWithdrawMapper;
import com.sz91online.bgms.module.payment.dao.PayWithdrawMapperExt;
import com.sz91online.bgms.module.payment.domain.PayUserBank;
import com.sz91online.bgms.module.payment.domain.PayWithdraw;
import com.sz91online.bgms.module.payment.service.PayBalanceService;
import com.sz91online.bgms.module.payment.service.PayUserBankService;
import com.sz91online.bgms.module.payment.service.PayWithdrawService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
@EnableCaching
public class PayWithdrawServiceImpl extends DefaultSearchService<PayWithdraw> implements PayWithdrawService  {

	@Autowired
	private PayWithdrawMapper payWithdrawMapper;
	
	@Autowired
	private PayWithdrawMapperExt payWithdrawMapperExt;
	
	@Autowired
	private PayBalanceService balanceService;
	
	@Autowired
	private PayUserBankService userBankService;
	
	@Override
	public ISearchableDAO getSearchMapper() {
		return payWithdrawMapperExt;
	}

	@Override
	public ICrudGenericDAO<PayWithdraw> getCrudMapper() {
		return payWithdrawMapper;
	}

	@Override
	@Transactional
	public void withdraw(PayWithdraw withdraw, String busiUserCode) {
		PayUserBank userBank = new PayUserBank();
		userBank.setAccountName(withdraw.getWdAccountName());
		userBank.setAccountNo(withdraw.getWdAccountNo());
		userBank.setBank(withdraw.getWdBank());
		userBank.setUserCode(busiUserCode);
		userBankService.saveBankInfo(userBank);
		
		balanceService.subForWithdraw(withdraw);
		this.saveWithSession(withdraw, busiUserCode);
	}

}
