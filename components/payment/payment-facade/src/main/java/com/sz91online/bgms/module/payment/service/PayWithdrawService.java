package com.sz91online.bgms.module.payment.service;

import com.sz91online.bgms.module.payment.domain.PayWithdraw;
import com.sz91online.common.db.service.IDefaultService;

public interface PayWithdrawService extends IDefaultService<PayWithdraw>{

	void withdraw(PayWithdraw withdraw, String busiUserCode);

}
