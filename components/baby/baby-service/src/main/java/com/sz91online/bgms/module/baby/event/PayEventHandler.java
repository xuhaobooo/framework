package com.sz91online.bgms.module.baby.event;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.eventbus.Subscribe;
import com.sz91online.bgms.eventbus.MyEventBus;
import com.sz91online.bgms.module.baby.domain.BabyInvite;
import com.sz91online.bgms.module.baby.domain.BabyPayment;
import com.sz91online.bgms.module.baby.domain.BabyRequire;
import com.sz91online.bgms.module.baby.domain.BabyTask;
import com.sz91online.bgms.module.baby.enums.BabyBusiTypeEnum;
import com.sz91online.bgms.module.baby.enums.BabyRequireStatusEnum;
import com.sz91online.bgms.module.baby.service.BabyInviteService;
import com.sz91online.bgms.module.baby.service.BabyPaymentService;
import com.sz91online.bgms.module.baby.service.BabyRequireService;
import com.sz91online.bgms.module.baby.service.BabyTaskService;
import com.sz91online.bgms.module.payment.domain.BusiFinishNotifyBean;
import com.sz91online.bgms.module.payment.domain.PayOrder;
import com.sz91online.bgms.module.payment.enums.PaymentStatusEnum;
import com.sz91online.common.eventbus.handler.AbstractEventHandler;

@Component
@Scope("singleton") // 必须是单例
public class PayEventHandler extends AbstractEventHandler {

	Logger logger = LoggerFactory.getLogger(PayEventHandler.class);

	@Autowired
	private BabyPaymentService babyPaymentService;
	
	@Autowired
	private BabyRequireService babyRequireService;
	
	@Autowired
	private BabyTaskService babyTaskService;
	
	@Autowired
	private BabyInviteService inviteService;
	
	@Autowired
	private MyEventBus eventBus;

	// 方法的参数类型必须与上面发送的类型一致，所在这些类型应该在service的基本项目中定义
	@Subscribe
	public void sub(PayOrder payment) {
		logger.debug("有支付消息：" + payment.getBusiCode() + ", payId:" + payment.getPayId() + ", amount:"
				+ payment.getAmount() + ", busiType:" + payment.getBusiType());

		if (PaymentStatusEnum.NEW.getValue().equals(payment.getOrderStatus())) {

			BabyPayment queryBean = new BabyPayment();
			queryBean.setBusiPaymentCode1(payment.getBusiCode());
			queryBean.setPaymentStatus(PaymentStatusEnum.NEW.getValue());
			List<BabyPayment> resultBean = babyPaymentService.findBySelective(queryBean);

			for (Iterator iterator = resultBean.iterator(); iterator.hasNext();) {
				BabyPayment babyPayment = (BabyPayment) iterator.next();
				babyPayment.setPaymentStatus(PaymentStatusEnum.FAIL.getValue());
				babyPaymentService.updateByPrimaryKeySelective(babyPayment, payment.getFromUserCode());
			}

			BabyPayment babyPayment = new BabyPayment();
			babyPayment.setAmount(payment.getAmount().floatValue());
			babyPayment.setBusiPaymentCode1(payment.getBusiCode());
			babyPayment.setPaymentType(payment.getBusiType());
			babyPayment.setCreateTime(null);
			babyPayment.setLastUpdateTime(null);
			babyPayment.setPaymentStatus(PaymentStatusEnum.NEW.getValue());

			babyPaymentService.saveWithSession(babyPayment, payment.getFromUserCode());

		} else if (PaymentStatusEnum.SUCC.getValue().equals(payment.getOrderStatus())) {
			BabyPayment queryBean = new BabyPayment();
			queryBean.setBusiPaymentCode1(payment.getBusiCode());
			queryBean.setPaymentStatus(PaymentStatusEnum.NEW.getValue());
			BabyPayment resultBean = babyPaymentService.findOne(queryBean);

			if (resultBean != null) {
				resultBean.setPaymentStatus(PaymentStatusEnum.SUCC.getValue());
				babyPaymentService.updateByPrimaryKeySelective(resultBean, payment.getFromUserCode());
				//保证支付并完成的需求的最终状态为AF
				verifyRequire(resultBean.getBusiPaymentCode1());
			} else {

				BabyPayment queryBean2 = new BabyPayment();
				queryBean2.setBusiPaymentCode1(payment.getBusiCode());
				queryBean2.setPaymentStatus(PaymentStatusEnum.SUCC.getValue());
				BabyPayment resultBean2 = babyPaymentService.findOne(queryBean2);
				if (resultBean2 == null) {
					queryBean.setAmount(payment.getAmount().floatValue());
					queryBean.setPaymentType(payment.getBusiType());
					queryBean.setCreateTime(null);
					queryBean.setLastUpdateTime(null);
					queryBean.setPaymentStatus(PaymentStatusEnum.SUCC.getValue());
					babyPaymentService.saveWithSession(queryBean, payment.getFromUserCode());
					//保证支付并完成的需求的最终状态为AF
					verifyRequire(queryBean.getBusiPaymentCode1());
				} else {
					logger.error("订单重复支付：" + queryBean2.getBusiPaymentCode1());
				}

			}

		} else if (PaymentStatusEnum.FAIL.getValue().equals(payment.getOrderStatus())) {
			BabyPayment babyPayment = new BabyPayment();
			babyPayment.setBusiPaymentCode1(payment.getBusiCode());
			babyPayment.setPaymentStatus(PaymentStatusEnum.NEW.getValue());
			List<BabyPayment> resultBean = babyPaymentService.findBySelective(babyPayment);

			if (resultBean != null && resultBean.size() > 0) {
				for (Iterator iterator = resultBean.iterator(); iterator.hasNext();) {
					BabyPayment updateBean = (BabyPayment) iterator.next();
					updateBean.setPaymentStatus(PaymentStatusEnum.FAIL.getValue());
					babyPaymentService.updateByPrimaryKeySelective(updateBean, payment.getFromUserCode());
				}
			} else {
				babyPayment.setAmount(payment.getAmount().floatValue());
				babyPayment.setPaymentType(payment.getBusiType());
				babyPayment.setCreateTime(null);
				babyPayment.setLastUpdateTime(null);
				babyPayment.setPaymentStatus(PaymentStatusEnum.FAIL.getValue());
				babyPaymentService.saveWithSession(babyPayment, payment.getFromUserCode());
			}
		} else {
			logger.error("不支持的支付状态，请检查！");
		}
	}

	/**
	 * 确定需求状态，如果支付成功，而需求状态还为CF，而把状态改为AF
	 * @param requireCode
	 */
	@Transactional
	public void verifyRequire(String requireCode) {
		BabyRequire qureyBean = new BabyRequire();
		qureyBean.setRequireCode(requireCode);
		qureyBean.setRequireStatus(BabyRequireStatusEnum.CUSTOMER_FINISHED.getValue());
		BabyRequire resultBean = babyRequireService.findOne(qureyBean);
		if(requireCode != null) {
			resultBean.setRequireStatus(BabyRequireStatusEnum.FINISHED.getValue());
			String userCode = resultBean.getUserCode();
			babyRequireService.updateWithSession(resultBean, userCode);
			
			// 把任务状态改为完成
			BabyTask queryBean = new BabyTask();
			queryBean.setRequireCode(requireCode);
			BabyTask curTask = babyTaskService.findOne(queryBean);
			if(curTask !=null){
				curTask.setTaskStatus(BabyRequireStatusEnum.FINISHED.getValue());
				curTask.setEndTime(new Date());
				babyTaskService.updateWithSession(curTask, userCode);

				BusiFinishNotifyBean notifyBean = new BusiFinishNotifyBean();
				notifyBean.setAmount(resultBean.getFeeAmount());
				notifyBean.setBusiCode(resultBean.getRequireCode());
				notifyBean.setBusiType(BabyBusiTypeEnum.WORK.getValue());
				notifyBean.setTime(new Date());
				notifyBean.setUserCode(curTask.getGetUserCode());

				BabyInvite inviteQueryBean = new BabyInvite();
				inviteQueryBean.setUserCode(userCode);
				inviteQueryBean.setEnable(true);
				BabyInvite inviteResultBean = inviteService.findOne(inviteQueryBean);
				if (inviteResultBean != null) {
					notifyBean.setInviteUserCode(inviteResultBean.getInviteUserCode());
					inviteResultBean.setEnable(false);
					inviteService.updateByPrimaryKeySelective(inviteResultBean, inviteResultBean.getInviteUserCode());
				}

				eventBus.post(notifyBean);
			}
			
		}
		
	}
}
