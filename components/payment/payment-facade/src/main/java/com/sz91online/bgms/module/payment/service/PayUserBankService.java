package com.sz91online.bgms.module.payment.service;

import com.sz91online.bgms.module.payment.domain.PayUserBank;
import com.sz91online.common.db.service.IDefaultService;

public interface PayUserBankService extends IDefaultService<PayUserBank>{

	void saveBankInfo(PayUserBank userBank);

}
