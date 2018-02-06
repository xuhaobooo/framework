package com.sz91online.bgms.module.payment.service;

import java.util.Date;
import java.util.List;

import com.sz91online.bgms.module.payment.domain.PayOrder;
import com.sz91online.bgms.module.payment.domain.SimplePayOrder;
import com.sz91online.common.db.service.IDefaultService;

public interface PayOrderService extends IDefaultService<PayOrder>{

	List<SimplePayOrder> findMine(String userCode, Date startTime, Date endTime);

}
