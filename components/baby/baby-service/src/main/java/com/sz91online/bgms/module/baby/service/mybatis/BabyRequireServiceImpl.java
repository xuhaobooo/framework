package com.sz91online.bgms.module.baby.service.mybatis;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sz91online.baby.module.user.utils.LatLng;
import com.sz91online.baby.module.user.utils.MapUtil;
import com.sz91online.bgms.eventbus.MyEventBus;
import com.sz91online.bgms.module.baby.dao.BabyRequireMapper;
import com.sz91online.bgms.module.baby.dao.BabyRequireMapperExt;
import com.sz91online.bgms.module.baby.domain.BabyApply;
import com.sz91online.bgms.module.baby.domain.BabyInvite;
import com.sz91online.bgms.module.baby.domain.BabyPayment;
import com.sz91online.bgms.module.baby.domain.BabyRequire;
import com.sz91online.bgms.module.baby.domain.BabyRequireItem;
import com.sz91online.bgms.module.baby.domain.BabyRequireItemDict;
import com.sz91online.bgms.module.baby.domain.BabyTask;
import com.sz91online.bgms.module.baby.domain.BabyTaskStep;
import com.sz91online.bgms.module.baby.domain.BabyUserInfo;
import com.sz91online.bgms.module.baby.domain.SimpleBabyApply;
import com.sz91online.bgms.module.baby.domain.SimpleBabyRequire;
import com.sz91online.bgms.module.baby.domain.SimpleBabyTask;
import com.sz91online.bgms.module.baby.domain.SimpleBabyTaskStep;
import com.sz91online.bgms.module.baby.domain.SimpleBabyUserInfo;
import com.sz91online.bgms.module.baby.enums.BabyApplyStatusEnum;
import com.sz91online.bgms.module.baby.enums.BabyBusiTypeEnum;
import com.sz91online.bgms.module.baby.enums.BabyRequireStatusEnum;
import com.sz91online.bgms.module.baby.exceptions.EBabyException;
import com.sz91online.bgms.module.baby.service.BabyApplyService;
import com.sz91online.bgms.module.baby.service.BabyInviteService;
import com.sz91online.bgms.module.baby.service.BabyPaymentService;
import com.sz91online.bgms.module.baby.service.BabyRequireItemDictService;
import com.sz91online.bgms.module.baby.service.BabyRequireItemService;
import com.sz91online.bgms.module.baby.service.BabyRequireService;
import com.sz91online.bgms.module.baby.service.BabyTaskService;
import com.sz91online.bgms.module.baby.service.BabyTaskStepService;
import com.sz91online.bgms.module.baby.service.BabyUserInfoService;
import com.sz91online.bgms.module.payment.domain.BusiFinishNotifyBean;
import com.sz91online.bgms.module.payment.enums.PaymentStatusEnum;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
public class BabyRequireServiceImpl extends DefaultSearchService<BabyRequire> implements BabyRequireService {

	@Autowired
	private BabyRequireMapper babyRequireMapper;

	@Autowired
	private BabyRequireMapperExt babyRequireMapperExt;

	@Autowired
	private BabyRequireItemService itemservice;

	@Autowired
	private BabyApplyService babyApplyService;

	@Autowired
	private BabyTaskService babyTaskService;

	@Autowired
	private BabyTaskStepService taskStepService;

	@Autowired
	private BabyRequireItemDictService dictService;

	@Autowired
	private BabyUserInfoService babyUserInfoService;

	@Autowired
	private BabyPaymentService babyPaymentService;

	@Autowired
	private BabyInviteService inviteService;

	@Autowired
	private MyEventBus eventBus;

	@Override
	public ISearchableDAO getSearchMapper() {
		return babyRequireMapperExt;
	}

	@Override
	public ICrudGenericDAO<BabyRequire> getCrudMapper() {
		return babyRequireMapper;
	}

	@Override
	@Transactional
	public void publish(SimpleBabyRequire babyRequire, String code) {

		this.saveWithSession(babyRequire, code);

		for (Iterator<BabyRequireItem> iterator = babyRequire.getItems().iterator(); iterator.hasNext();) {
			BabyRequireItem item = iterator.next();
			item.setRequireCode(babyRequire.getRequireCode());
			itemservice.saveWithSession(item, code);
		}

	}

	@Override
	public List<SimpleBabyRequire> listMy(String userCode, String status, Date startTime, Date endTime) {

		List<SimpleBabyRequire> requireList = babyRequireMapperExt.getMyRequire(userCode, status, startTime, endTime);

		for (Iterator<SimpleBabyRequire> iterator = requireList.iterator(); iterator.hasNext();) {
			SimpleBabyRequire babyRequire = iterator.next();

			BabyRequireItem itemQueryBean = new BabyRequireItem();
			itemQueryBean.setRequireCode(babyRequire.getRequireCode());
			List<BabyRequireItem> items = itemservice.findBySelective(itemQueryBean);

			babyRequire.setItems(items);

			SimpleBabyUserInfo userInfo = babyUserInfoService.findRequireApplySucc(babyRequire.getRequireCode());
			if (userInfo != null) {
				babyRequire.setCompanyCode(userInfo.getUserCode());
				babyRequire.setCompanyName(userInfo.getUserName());
				babyRequire.setCompanyType(userInfo.getUserRole());
				babyRequire.setCompanyTel(userInfo.getTel());
			}

			BabyPayment payQueryBean = new BabyPayment();
			payQueryBean.setBusiPaymentCode1(babyRequire.getRequireCode());
			payQueryBean.setPaymentStatus(PaymentStatusEnum.SUCC.getValue());
			BabyPayment resultPaymentBean = babyPaymentService.findOne(payQueryBean);
			if (resultPaymentBean != null) {
				babyRequire.setPaid(true);
			} else {
				babyRequire.setPaid(false);
			}

		}

		return requireList;
	}

	private SimpleBabyRequire convert(BabyRequire require) {
		SimpleBabyRequire result = new SimpleBabyRequire();
		result.setAddrName(require.getAddrName());
		result.setAddrPosX(require.getAddrPosX());
		result.setAddrPosY(require.getAddrPosY());
		result.setBabyAge(require.getBabyAge());
		result.setBabyName(require.getBabyName());
		result.setBabySex(require.getBabySex());
		result.setCreateTime(require.getCreateTime());
		result.setCreditCode(require.getCreditCode());
		result.setEndTime(require.getEndTime());
		result.setFeeAmount(require.getFeeAmount());
		result.setId(require.getId());
		result.setPayMore(require.getPayMore());
		result.setRequireCode(require.getRequireCode());
		result.setRequireStatus(require.getRequireStatus());
		result.setStartTime(require.getStartTime());
		result.setUserCode(require.getUserCode());
		return result;
	}

	@Override
	public SimpleBabyRequire findSingle(String requireCode) {

		SimpleBabyRequire resultBean = babyRequireMapperExt.findSimpleRequireByCode(requireCode);
		if (resultBean == null) {
			throw EBabyException.BABY_REQUIRE_NOT_EXIST_ERROR;
		}

		BabyRequireItem itemQueryBean = new BabyRequireItem();
		itemQueryBean.setRequireCode(requireCode);
		List<BabyRequireItem> items = itemservice.findBySelective(itemQueryBean);

		resultBean.setItems(items);

		SimpleBabyUserInfo userInfo = babyUserInfoService.findRequireApplySucc(resultBean.getRequireCode());
		if (userInfo != null) {
			resultBean.setCompanyCode(userInfo.getUserCode());
			resultBean.setCompanyName(userInfo.getUserName());
			resultBean.setCompanyType(userInfo.getUserRole());
			resultBean.setCompanyTel(userInfo.getTel());
		}

		List<SimpleBabyTaskStep> steps = taskStepService.findStepsByRequireCode(resultBean.getRequireCode());
		resultBean.setStepList(steps);

		BabyPayment payQueryBean = new BabyPayment();
		payQueryBean.setBusiPaymentCode1(requireCode);
		payQueryBean.setPaymentStatus(PaymentStatusEnum.SUCC.getValue());
		BabyPayment resultPaymentBean = babyPaymentService.findOne(payQueryBean);
		if (resultPaymentBean != null) {
			resultBean.setPaid(true);
		} else {
			resultBean.setPaid(false);
		}

		return resultBean;

	}

	@Override
	public List<SimpleBabyApply> findApply(String requireCode) {
		BabyApply queryBean = new BabyApply();
		queryBean.setRequireCode(requireCode);
		List<BabyApply> list = babyApplyService.findBySelective(queryBean);

		List<SimpleBabyApply> resultList = new ArrayList<>();

		BabyRequire requireQueryBean = new BabyRequire();
		requireQueryBean.setRequireCode(requireCode);
		BabyRequire requireResultBean = this.findOne(requireQueryBean);
		if (requireResultBean == null) {
			return resultList;
		}

		for (Iterator<BabyApply> iterator = list.iterator(); iterator.hasNext();) {
			BabyApply babyApply = iterator.next();
			SimpleBabyApply bean = new SimpleBabyApply();
			bean.setApplyStatus(babyApply.getApplyStatus());
			bean.setCreateTime(babyApply.getCreateTime());
			bean.setId(babyApply.getId());
			bean.setRequireCode(babyApply.getRequireCode());
			bean.setUserCode(babyApply.getUserCode());

			BabyUserInfo userQueryBean = new BabyUserInfo();
			userQueryBean.setUserCode(babyApply.getUserCode());
			BabyUserInfo userInfo = babyUserInfoService.findOne(userQueryBean);
			if (userInfo != null) {
				bean.setAddrName(userInfo.getAddrName());
				bean.setAddrPosX(userInfo.getAddrPosX());
				bean.setAddrPosY(userInfo.getAddrPosY());
				bean.setUserName(userInfo.getUserName());
				bean.setUserRole(userInfo.getUserRole());
				bean.setTel(userInfo.getTel());
				bean.setNote(userInfo.getNote());
				bean.setCreditValue(80);
				if (userInfo.getAddrPosX() == null || userInfo.getAddrPosY() == null
						|| userInfo.getAddrPosX().compareTo(0D) == 0 || userInfo.getAddrPosY().compareTo(0D) == 0) {
					bean.setDistance(-1D);
				} else {
					LatLng userPos = new LatLng(userInfo.getAddrPosY(), userInfo.getAddrPosX());
					LatLng requirePos = new LatLng(requireResultBean.getAddrPosY(), requireResultBean.getAddrPosX());
					bean.setDistance(MapUtil.getLatLngDistance(userPos, requirePos));
				}
			} else {
				bean.setDistance(-1.0);
			}

			resultList.add(bean);
		}
		return resultList;
	}

	@Override
	@Transactional
	public void select(Long applyId) {

		// 判断申请是否存在
		BabyApply curApply = babyApplyService.findByPrimaryKey(applyId);
		if (curApply == null) {
			throw EBabyException.BABY_APPLY_NOT_EXIST;
		}

		// 判断是否已有机构被选中
		List<SimpleBabyApply> allApply = findApply(curApply.getRequireCode());
		for (Iterator<SimpleBabyApply> iterator = allApply.iterator(); iterator.hasNext();) {
			SimpleBabyApply babyApply = iterator.next();
			if (BabyApplyStatusEnum.SUCC.getValue().equals(babyApply.getApplyStatus())) {
				throw EBabyException.BABY_REQUIRE_HAS_APPLY;
			}
		}

		// 判断需求是否存在
		BabyRequire queryBean = new BabyRequire();
		queryBean.setRequireCode(curApply.getRequireCode());
		BabyRequire returnRequireBean = this.findOne(queryBean);

		if (returnRequireBean == null) {
			throw EBabyException.BABY_REQUIRE_NOT_EXIST_ERROR;
		}

		returnRequireBean.setRequireStatus(BabyRequireStatusEnum.CONFIRMED.getValue());
		this.updateWithSession(returnRequireBean, returnRequireBean.getUserCode());

		// 修改选中状态
		curApply.setApplyStatus(BabyApplyStatusEnum.SUCC.getValue());
		babyApplyService.updateWithSession(curApply, returnRequireBean.getUserCode());

		// 把其他申请者的状态改成失败
		babyApplyService.updateFail(returnRequireBean.getRequireCode(), applyId);

		// 新建任务
		BabyTask task = new BabyTask();
		task.setStartTime(new Date());
		task.setGetUserCode(curApply.getUserCode());
		task.setRequireCode(curApply.getRequireCode());
		task.setSendUserCode(returnRequireBean.getUserCode());
		task.setTaskCode("Tk" + new Date().getTime());
		task.setTaskStatus(BabyRequireStatusEnum.CONFIRMED.getValue());
		babyTaskService.saveWithSession(task, returnRequireBean.getUserCode());

	}

	@Override
	public SimpleBabyTask findTask(String requireCode) {

		SimpleBabyTask resultBean = babyTaskService.findSimpleTask(requireCode);
		BabyRequireItem itemQueryBean = new BabyRequireItem();
		itemQueryBean.setRequireCode(resultBean.getRequireCode());
		List<BabyRequireItem> items = itemservice.findBySelective(itemQueryBean);
		String itemCon = "";
		for (Iterator iterator2 = items.iterator(); iterator2.hasNext();) {
			BabyRequireItem babyRequireItem = (BabyRequireItem) iterator2.next();
			itemCon = itemCon + babyRequireItem.getItemName();
			if (iterator2.hasNext()) {
				itemCon = itemCon + ",";
			}
		}
		resultBean.setRequireItems(itemCon);

		BabyPayment payQueryBean = new BabyPayment();
		payQueryBean.setBusiPaymentCode1(resultBean.getRequireCode());
		payQueryBean.setPaymentStatus(PaymentStatusEnum.SUCC.getValue());
		BabyPayment resultPaymentBean = babyPaymentService.findOne(payQueryBean);
		if (resultPaymentBean != null) {
			resultBean.setPaid(true);
		} else {
			resultBean.setPaid(false);
		}
		return resultBean;
	}

	@Override
	@Transactional
	public void apply(String requireCode, String userCode) {

		BabyApply queryBean = new BabyApply();
		queryBean.setRequireCode(requireCode);
		queryBean.setUserCode(userCode);

		BabyApply resultBean = babyApplyService.findOne(queryBean);
		if (resultBean != null) {
			throw EBabyException.BABY_REQUIRE_APPLIED;
		}

		queryBean.setApplyStatus(BabyApplyStatusEnum.PENDING.getValue());
		queryBean.setCreateTime(new Date());
		babyApplyService.saveWithSession(queryBean, userCode);

	}

	@Override
	@Transactional
	public void arrive(String taskCode, String userCode) {

		changeTaskStatus(taskCode, userCode, BabyRequireStatusEnum.CONFIRMED.getValue(),
				BabyRequireStatusEnum.ARRIVED.getValue(), "到达目的地");
	}

	private void changeTaskStatus(String taskCode, String userCode, String fromStatus, String toStatus, String msg) {
		BabyTask queryBean = new BabyTask();
		queryBean.setGetUserCode(userCode);
		queryBean.setTaskCode(taskCode);
		BabyTask resultBean = babyTaskService.findOne(queryBean);

		if (resultBean == null) {
			throw EBabyException.BABY_TASK_NOT_OWNER;
		}

		if (!fromStatus.equals(resultBean.getTaskStatus())) {
			throw EBabyException.BABY_TASK_STATUS_ERROR;
		}

		// 把需求状态改为 到达
		BabyRequire requireQueryBean = new BabyRequire();
		requireQueryBean.setRequireCode(resultBean.getRequireCode());
		BabyRequire requireResultBean = this.findOne(requireQueryBean);

		if (requireResultBean == null) {
			throw EBabyException.BABY_REQUIRE_NOT_EXIST_ERROR;
		} else {
			requireResultBean.setRequireStatus(toStatus);
			this.updateWithSession(requireResultBean, userCode);
		}

		// 把任务状态改为到达
		resultBean.setTaskStatus(toStatus);
		babyTaskService.updateWithSession(resultBean, userCode);

		// 添加任务step
		BabyTaskStep step = new BabyTaskStep();
		step.setDoneTime(new Date());
		step.setStepContent(msg);
		step.setTaskCode(taskCode);
		taskStepService.saveWithSession(step, userCode);
	}

	@Override
	@Transactional
	public void provideFinish(String taskCode, String userCode) {
		changeTaskStatus(taskCode, userCode, BabyRequireStatusEnum.ARRIVED.getValue(),
				BabyRequireStatusEnum.PROVIDE_FINISHED.getValue(), "服务已完成");

	}

	@Override
	@Transactional
	public void customerFinish(String requireCode, String userCode) {

		BabyTask queryBean = new BabyTask();
		queryBean.setRequireCode(requireCode);
		BabyTask curTask = babyTaskService.findOne(queryBean);

		if (curTask == null) {
			throw EBabyException.BABY_TASK_NOT_EXIST;
		}

		if (!BabyRequireStatusEnum.PROVIDE_FINISHED.getValue().equals(curTask.getTaskStatus())) {
			throw EBabyException.BABY_TASK_STATUS_ERROR;
		}

		BabyPayment payQueryBean = new BabyPayment();
		payQueryBean.setBusiPaymentCode1(curTask.getRequireCode());
		payQueryBean.setPaymentStatus(PaymentStatusEnum.SUCC.getValue());
		BabyPayment resultPaymentBean = babyPaymentService.findOne(payQueryBean);

		// 把需求状态改为 完成
		BabyRequire requireQueryBean = new BabyRequire();
		requireQueryBean.setRequireCode(curTask.getRequireCode());
		BabyRequire requireResultBean = this.findOne(requireQueryBean);

		if (requireResultBean == null) {
			throw EBabyException.BABY_REQUIRE_NOT_EXIST_ERROR;
		}
		if (resultPaymentBean != null) {
			
			// 添加任务step
			BabyTaskStep step = new BabyTaskStep();
			step.setDoneTime(new Date());
			step.setStepContent("用户已确认完成");
			step.setTaskCode(curTask.getTaskCode());
			taskStepService.saveWithSession(step, userCode);
			
			requireResultBean.setRequireStatus(BabyRequireStatusEnum.FINISHED.getValue());
			this.updateWithSession(requireResultBean, userCode);

			// 把任务状态改为完成
			curTask.setTaskStatus(BabyRequireStatusEnum.FINISHED.getValue());
			curTask.setEndTime(new Date());
			babyTaskService.updateWithSession(curTask, userCode);

			BusiFinishNotifyBean notifyBean = new BusiFinishNotifyBean();
			notifyBean.setAmount(requireResultBean.getFeeAmount());
			notifyBean.setBusiCode(requireResultBean.getRequireCode());
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

		}else {
			throw EBabyException.MUST_AFTER_PAYMENT;
		}

	}

	@Override
	public List<BabyRequireItemDict> getAllService() {
		BabyRequireItemDict queryBean = new BabyRequireItemDict();
		queryBean.setEnableFlag(true);
		return dictService.findBySelective(queryBean);
	}

	@Override
	public List<SimpleBabyRequire> listRequire(String userCode, Date startTime, Date endTime) {

		BabyUserInfo babyInfoQueryBean = new BabyUserInfo();
		babyInfoQueryBean.setUserCode(userCode);
		BabyUserInfo userInfo = babyUserInfoService.findOne(babyInfoQueryBean);
		if (userInfo == null) {
			throw EBabyException.USER_INFO_ERROR;
		}

		List<SimpleBabyRequire> requireList = babyRequireMapperExt.listRequire(startTime, endTime,
				userInfo.getAddrPosX(), userInfo.getAddrPosY());
		List<SimpleBabyRequire> resultList = new ArrayList<>();

		for (Iterator<SimpleBabyRequire> iterator = requireList.iterator(); iterator.hasNext();) {
			SimpleBabyRequire babyRequire = iterator.next();

			// 这里是游客的代码处理，先直接用固定账号判断，以后再处理
			if ("Usjigou1231543434".equals(userCode) && !"Usyouke1231543434".equals(babyRequire.getUserCode())) {
				continue;
			}

			if (!"Usjigou1231543434".equals(userCode) && "Usyouke1231543434".equals(babyRequire.getUserCode())) {
				continue;
			}

			BabyRequireItem itemQueryBean = new BabyRequireItem();
			itemQueryBean.setRequireCode(babyRequire.getRequireCode());
			List<BabyRequireItem> items = itemservice.findBySelective(itemQueryBean);

			babyRequire.setItems(items);

			BabyApply applyQueryBean = new BabyApply();
			applyQueryBean.setUserCode(userCode);
			applyQueryBean.setRequireCode(babyRequire.getRequireCode());
			BabyApply resultBean = babyApplyService.findOne(applyQueryBean);

			if (resultBean != null) {
				babyRequire.setApplied(true);
			} else {
				babyRequire.setApplied(false);
			}

			BabyUserInfo userInfoQueryBean = new BabyUserInfo();
			userInfoQueryBean.setUserCode(userCode);
			BabyUserInfo userInfoResultBean = babyUserInfoService.findOne(userInfoQueryBean);
			if (userInfoResultBean == null || userInfoResultBean.getAddrPosX() == null
					|| userInfoResultBean.getAddrPosY() == null || userInfoResultBean.getAddrPosX().compareTo(0D) == 0
					|| userInfoResultBean.getAddrPosY().compareTo(0D) == 0) {
				babyRequire.setDistance(-1D);
			} else {
				LatLng userPos = new LatLng(userInfoResultBean.getAddrPosY(), userInfoResultBean.getAddrPosX());
				LatLng requirePos = new LatLng(babyRequire.getAddrPosY(), babyRequire.getAddrPosX());
				babyRequire.setDistance(MapUtil.getLatLngDistance(userPos, requirePos));
			}

			resultList.add(babyRequire);
		}

		return resultList;
	}

	@Override
	public List<SimpleBabyTask> listTasks(String userCode, String status, Date startTime, Date endTime) {
		List<SimpleBabyTask> resultBeanList = babyTaskService.listTasks(userCode, status, startTime, endTime);
		for (Iterator iterator = resultBeanList.iterator(); iterator.hasNext();) {
			SimpleBabyTask simpleBabyTask = (SimpleBabyTask) iterator.next();

			BabyRequireItem itemQueryBean = new BabyRequireItem();
			itemQueryBean.setRequireCode(simpleBabyTask.getRequireCode());
			List<BabyRequireItem> items = itemservice.findBySelective(itemQueryBean);
			String itemCon = "";
			for (Iterator iterator2 = items.iterator(); iterator2.hasNext();) {
				BabyRequireItem babyRequireItem = (BabyRequireItem) iterator2.next();
				itemCon = itemCon + babyRequireItem.getItemName();
				if (iterator2.hasNext()) {
					itemCon = itemCon + ",";
				}
			}
			simpleBabyTask.setRequireItems(itemCon);

			BabyPayment payQueryBean = new BabyPayment();
			payQueryBean.setBusiPaymentCode1(simpleBabyTask.getRequireCode());
			payQueryBean.setPaymentStatus(PaymentStatusEnum.SUCC.getValue());
			BabyPayment resultPaymentBean = babyPaymentService.findOne(payQueryBean);
			if (resultPaymentBean != null) {
				simpleBabyTask.setPaid(true);
			} else {
				simpleBabyTask.setPaid(false);
			}
		}
		return resultBeanList;
	}

	@Override
	public List<SimpleBabyTask> listDoneTasks(String userCode) {
		List<SimpleBabyTask> resultBean = babyTaskService.listDoneTasks(userCode);
		return resultBean;
	}

	@Override
	public void mockPay(String requireCode, String code) {
		BabyPayment saveBean = new BabyPayment();
		saveBean.setBusiPaymentCode1(requireCode);

		BabyPayment result = babyPaymentService.findOne(saveBean);
		if (result == null) {
			saveBean.setAmount(0.01F);
			saveBean.setCreateTime(new Date());
			saveBean.setPaymentStatus("SUCC");
			saveBean.setPaymentType("BZJ");
			babyPaymentService.saveWithSession(saveBean, code);
		}

	}

	@Override
	@Transactional
	public void cancelRequire(String userCode, String requireCode) {

		BabyRequire queryBean = new BabyRequire();
		queryBean.setRequireCode(requireCode);
		BabyRequire resultBean = this.findOne(queryBean);

		if (resultBean == null) {
			throw EBabyException.BABY_REQUIRE_NOT_EXIST_ERROR;
		} else {

			if (!userCode.equals(resultBean.getUserCode())) {
				throw EBabyException.BABY_REQUIRE_NOT_OWNER;
			}

			if (BabyRequireStatusEnum.NEW.getValue().equals(resultBean.getRequireStatus())
					|| BabyRequireStatusEnum.CONFIRMED.getValue().equals(resultBean.getRequireStatus())) {
				babyRequireMapperExt.setRequireStatus(requireCode, BabyRequireStatusEnum.CANCEL.getValue());
				babyTaskService.cancleTask(userCode, requireCode);
			} else if (BabyRequireStatusEnum.CANCEL.getValue().equals(resultBean.getRequireStatus())) {
				throw EBabyException.BABY_REQUIRE_HAVE_CANCELED;
			} else {
				throw EBabyException.BABY_REQUIRE_CANT_BEEN_CANCEL;
			}

		}

	}

	@Override
	public void cancelTask(String userCode, String taskCode) {

		BabyTask taskQueryBean = new BabyTask();
		taskQueryBean.setTaskCode(taskCode);
		BabyTask taskResultBean = babyTaskService.findOne(taskQueryBean);
		if (taskResultBean == null) {
			throw EBabyException.BABY_TASK_NOT_EXIST;
		}
		if (!taskResultBean.getGetUserCode().equals(userCode)) {
			throw EBabyException.BABY_TASK_NOT_OWNER;
		}

		String requireCode = taskResultBean.getRequireCode();
		SimpleBabyRequire resultBean = this.findSingle(requireCode);

		if (resultBean == null) {
			throw EBabyException.BABY_REQUIRE_NOT_EXIST_ERROR;
		} else {

			if (!resultBean.isPaid()) {
				babyRequireMapperExt.setRequireStatus(requireCode, BabyRequireStatusEnum.CANCEL.getValue());
				babyTaskService.cancleTask(userCode, requireCode);
			} else {
				throw EBabyException.BABY_REQUIRE_CANT_BEEN_CANCEL;
			}

		}
	}

}
