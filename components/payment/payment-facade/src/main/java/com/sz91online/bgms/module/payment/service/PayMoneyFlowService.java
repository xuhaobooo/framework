package com.sz91online.bgms.module.payment.service;

import java.util.Date;
import java.util.List;

import com.sz91online.bgms.module.payment.domain.PayMoneyFlow;
import com.sz91online.bgms.module.payment.domain.SimplePayMoneyFlow;
import com.sz91online.common.db.service.IDefaultService;

public interface PayMoneyFlowService extends IDefaultService<PayMoneyFlow>{

	List<SimplePayMoneyFlow> findMine(String code, Date startTime, Date date);

}
