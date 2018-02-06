package com.sz91online.bgms.module.payment.service.mybatis;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sz91online.bgms.module.payment.dao.PayBalanceMapper;
import com.sz91online.bgms.module.payment.dao.PayBalanceMapperExt;
import com.sz91online.bgms.module.payment.domain.BusiFinishNotifyBean;
import com.sz91online.bgms.module.payment.domain.PayBalance;
import com.sz91online.bgms.module.payment.domain.PayConfig;
import com.sz91online.bgms.module.payment.domain.PayMoneyFlow;
import com.sz91online.bgms.module.payment.domain.PayOrder;
import com.sz91online.bgms.module.payment.domain.PayWithdraw;
import com.sz91online.bgms.module.payment.enums.PaymentStatusEnum;
import com.sz91online.bgms.module.payment.exceptions.EPaymentException;
import com.sz91online.bgms.module.payment.service.PayBalanceService;
import com.sz91online.bgms.module.payment.service.PayConfigService;
import com.sz91online.bgms.module.payment.service.PayMoneyFlowService;
import com.sz91online.bgms.module.payment.service.PayOrderService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;
import com.sz91online.common.utils.PlIdWork;

@Service
public class PayBalanceServiceImpl extends DefaultSearchService<PayBalance> implements PayBalanceService {

	Logger logger = LoggerFactory.getLogger(PayBalanceServiceImpl.class);

	@Autowired
	private PayBalanceMapper payBalanceMapper;

	@Autowired
	private PayBalanceMapperExt payBalanceMapperExt;

	@Autowired
	private PayMoneyFlowService moneyFlowService;

	@Autowired
	private PayOrderService orderService;

	@Autowired
	private PayConfigService configService;

	@Override
	public ISearchableDAO getSearchMapper() {
		return payBalanceMapperExt;
	}

	@Override
	public ICrudGenericDAO<PayBalance> getCrudMapper() {
		return payBalanceMapper;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public PayBalance findOne(PayBalance bean) {
		PayBalance balanceResultBean = super.findOne(bean);
		if (balanceResultBean != null) {
			return balanceResultBean;
		} else {
			PayBalance saveBean = new PayBalance();
			saveBean.setUserCode(bean.getUserCode());
			saveBean.setBalanceAmount(new BigDecimal(0.0));
			this.saveWithSession(saveBean, saveBean.getUserCode());
			return saveBean;
		}
	}

	@Override
	@Transactional
	public void sub(PayOrder saveBean) {

		PayBalance balancequeryBean = new PayBalance();
		balancequeryBean.setUserCode(saveBean.getFromUserCode());
		PayBalance balanceResultBean = this.findOne(balancequeryBean);

		if (balanceResultBean.getBalanceAmount().compareTo(saveBean.getAmount()) == -1) {
			throw EPaymentException.NOT_ENOUGH_MONEY;
		} else {
			balanceResultBean.setBalanceAmount(balanceResultBean.getBalanceAmount().subtract(saveBean.getAmount()));
			balanceResultBean.setLastModifyDate(new Date());
			this.updateByPrimaryKeySelective(balanceResultBean, balanceResultBean.getUserCode());
		}

		PayMoneyFlow moneyFlow = new PayMoneyFlow();
		moneyFlow.setAmount(new BigDecimal(0).subtract(saveBean.getAmount()));
		moneyFlow.setBusiType(saveBean.getBusiType());
		moneyFlow.setBusiUserCode(saveBean.getFromUserCode());
		moneyFlow.setOrderRecordCode(saveBean.getOrderRecordCode());
		moneyFlow.setRecordTime(new Date());
		moneyFlow.setUnit("元");
		moneyFlowService.saveWithSession(moneyFlow, saveBean.getFromUserCode());
	}

	@Override
	@Transactional
	public void add(BusiFinishNotifyBean notifyBean) {
		PayBalance balancequeryBean = new PayBalance();
		balancequeryBean.setUserCode(notifyBean.getUserCode());
		PayBalance balanceResultBean = this.findOne(balancequeryBean);
		balanceResultBean
				.setBalanceAmount(balanceResultBean.getBalanceAmount().add(new BigDecimal(notifyBean.getAmount())));
		balanceResultBean.setLastModifyDate(new Date());
		this.updateByPrimaryKeySelective(balanceResultBean, balanceResultBean.getUserCode());

		PayOrder orderQueryBean = new PayOrder();
		orderQueryBean.setBusiCode(notifyBean.getBusiCode());
		orderQueryBean.setOrderStatus(PaymentStatusEnum.SUCC.getValue());
		PayOrder orderResultBean = orderService.findOne(orderQueryBean);

		PayMoneyFlow moneyFlow = new PayMoneyFlow();
		moneyFlow.setAmount(new BigDecimal(notifyBean.getAmount()));
		moneyFlow.setBusiType(notifyBean.getBusiType());
		moneyFlow.setBusiUserCode(notifyBean.getUserCode());
		moneyFlow.setOrderRecordCode(orderResultBean.getOrderRecordCode());
		moneyFlow.setRecordTime(new Date());
		moneyFlow.setUnit("元");
		moneyFlowService.saveWithSession(moneyFlow, moneyFlow.getBusiUserCode());
	}

	@Override
	@Transactional
	public void addInvite(String userCode, String inviteUserCode) {

		PayConfig configQueryBean = new PayConfig();
		configQueryBean.setCode("inviteM");
		PayConfig configResultBean = configService.findOne(configQueryBean);

		PayBalance balancequeryBean = new PayBalance();
		balancequeryBean.setUserCode(inviteUserCode);
		PayBalance balanceResultBean = this.findOne(balancequeryBean);
		balanceResultBean.setBalanceAmount(
				balanceResultBean.getBalanceAmount().add(new BigDecimal(configResultBean.getValue())));
		balanceResultBean.setLastModifyDate(new Date());
		this.updateByPrimaryKeySelective(balanceResultBean, balanceResultBean.getUserCode());

		PayMoneyFlow moneyFlow = new PayMoneyFlow();
		moneyFlow.setAmount(new BigDecimal(configResultBean.getValue()));
		moneyFlow.setBusiType("IVT");
		moneyFlow.setBusiUserCode(inviteUserCode);
		moneyFlow.setOrderRecordCode("IV" + new PlIdWork().nextId());
		moneyFlow.setRecordTime(new Date());
		moneyFlow.setUnit("元");
		moneyFlowService.saveWithSession(moneyFlow, moneyFlow.getBusiUserCode());
	}

	@Override
	@Transactional
	public void subForWithdraw(PayWithdraw withdraw) {
		PayBalance balancequeryBean = new PayBalance();
		balancequeryBean.setUserCode(withdraw.getBusiUserCode());
		PayBalance balanceResultBean = this.findOne(balancequeryBean);

		if (balanceResultBean.getBalanceAmount().compareTo(withdraw.getWdAmount()) == -1) {
			throw EPaymentException.NOT_ENOUGH_MONEY;
		} else {
			balanceResultBean.setBalanceAmount(balanceResultBean.getBalanceAmount().subtract(withdraw.getWdAmount()));
			balanceResultBean.setLastModifyDate(new Date());
			this.updateByPrimaryKeySelective(balanceResultBean, balanceResultBean.getUserCode());
		}

		PayMoneyFlow moneyFlow = new PayMoneyFlow();
		moneyFlow.setAmount(new BigDecimal(0).subtract(withdraw.getWdAmount()));
		moneyFlow.setBusiType("TX");
		moneyFlow.setBusiUserCode(withdraw.getBusiUserCode());
		moneyFlow.setOrderRecordCode("TX" + new PlIdWork().nextId());
		moneyFlow.setRecordTime(new Date());
		moneyFlow.setUnit("元");
		moneyFlowService.saveWithSession(moneyFlow, withdraw.getBusiUserCode());

	}

	@Override
	public void addForWithdrawFail(PayWithdraw withdraw) {
		PayBalance balancequeryBean = new PayBalance();
		balancequeryBean.setUserCode(withdraw.getBusiUserCode());
		PayBalance balanceResultBean = this.findOne(balancequeryBean);

		balanceResultBean.setBalanceAmount(balanceResultBean.getBalanceAmount().add(withdraw.getWdAmount()));
		balanceResultBean.setLastModifyDate(new Date());
		this.updateByPrimaryKeySelective(balanceResultBean, balanceResultBean.getUserCode());

		PayMoneyFlow moneyFlow = new PayMoneyFlow();
		moneyFlow.setAmount(withdraw.getWdAmount());
		moneyFlow.setBusiType("TK");
		moneyFlow.setBusiUserCode(withdraw.getBusiUserCode());
		moneyFlow.setOrderRecordCode(withdraw.getWdCode());
		moneyFlow.setRecordTime(new Date());
		moneyFlow.setUnit("元");
		moneyFlowService.saveWithSession(moneyFlow, withdraw.getBusiUserCode());
	}

}
