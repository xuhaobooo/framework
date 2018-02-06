package com.sz91online.bgms.module.payment.service;

import com.sz91online.bgms.module.payment.domain.BusiFinishNotifyBean;
import com.sz91online.bgms.module.payment.domain.PayBalance;
import com.sz91online.bgms.module.payment.domain.PayOrder;
import com.sz91online.bgms.module.payment.domain.PayWithdraw;
import com.sz91online.common.db.service.IDefaultService;

public interface PayBalanceService extends IDefaultService<PayBalance>{

	void sub(PayOrder saveBean);

	void add(BusiFinishNotifyBean notifyBean);

	void addInvite(String userCode, String inviteUserCode);

	void subForWithdraw(PayWithdraw withdraw);

	void addForWithdrawFail(PayWithdraw withdraw);
}
