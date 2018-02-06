package com.sz91online.bgms.module.payment.service.mybatis;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.eventbus.Subscribe;
import com.sz91online.bgms.module.payment.domain.BusiFinishNotifyBean;
import com.sz91online.bgms.module.payment.domain.PayMoneyFlow;
import com.sz91online.bgms.module.payment.domain.PayOrder;
import com.sz91online.bgms.module.payment.enums.PaymentStatusEnum;
import com.sz91online.bgms.module.payment.service.PayBalanceService;
import com.sz91online.bgms.module.payment.service.PayMoneyFlowService;
import com.sz91online.bgms.module.payment.service.PayOrderService;
import com.sz91online.common.eventbus.handler.AbstractEventHandler;

@Component
@Scope("singleton") // 必须是单例
public class MoneyEventHandler extends AbstractEventHandler {

	Logger logger = LoggerFactory.getLogger(MoneyEventHandler.class);

	@Autowired
	private PayBalanceService balanceService;
	
	@Autowired
	private PayOrderService payOrderService;
	
	@Autowired
	private PayMoneyFlowService moneyFlowService;

	// 方法的参数类型必须与上面发送的类型一致，所在这些类型应该在service的基本项目中定义
	@Subscribe
	@Transactional
	public void sub(BusiFinishNotifyBean notifyBean) {
		logger.debug("有资金变动消息：" + notifyBean.getBusiCode() + ", userCode:" + notifyBean.getUserCode() + ", amount:"
				+ notifyBean.getAmount() + ", busiType:" + notifyBean.getBusiType());

		//确定不会重复执行
		PayOrder orderQueryBean = new PayOrder();
		orderQueryBean.setBusiCode(notifyBean.getBusiCode());
		orderQueryBean.setOrderStatus(PaymentStatusEnum.SUCC.getValue());
		PayOrder orderResultBean = payOrderService.findOne(orderQueryBean);
		if(orderResultBean != null){
			PayMoneyFlow queryBean =new PayMoneyFlow();
			queryBean.setAmount(new BigDecimal(notifyBean.getAmount()));
			queryBean.setBusiType(notifyBean.getBusiType());
			queryBean.setBusiUserCode(notifyBean.getUserCode());
			queryBean.setOrderRecordCode(orderResultBean.getOrderRecordCode());
			PayMoneyFlow resultBean = moneyFlowService.findOne(queryBean);
			if(resultBean == null){
				balanceService.add(notifyBean);
				if(notifyBean.getInviteUserCode() != null) {
					balanceService.addInvite(notifyBean.getUserCode(), notifyBean.getInviteUserCode());
				}
			}else{
				logger.error("有重复执行的业务完成支付请求");
			}
		}else{
			logger.error("业务完成支付请求的支付信息不存在");
		}
		
		
		
	}

}
