package com.sz91online.bgms.module.baby.exceptions;

import com.sz91online.common.exceptions.EBusinessException;

public class EBabyException extends EBusinessException{
	
	private static final long serialVersionUID = -2524275212267006703L;
	
	public EBabyException(String resultCode, String resultMsg) {
		super(resultCode, resultMsg);
	}
	
	public static final EBabyException BABY_REQUIRE_NOT_EXIST_ERROR = new EBabyException("10220001", "没有此需求");
	public static final EBabyException BABY_INTERFACE_OUT_OF_SERVICE = new EBabyException("10220002", "此接口不能被调用");
	
	public static final EBabyException BABY_REQUIRE_HAS_APPLY = new EBabyException("10220003", "此需求已有被选中的机构提供服务，不能重选！");
	public static final EBabyException BABY_APPLY_NOT_EXIST = new EBabyException("10220004", "不存在此申请！");
	
	public static final EBabyException BABY_REQUIRE_APPLIED = new EBabyException("10220005", "你已经申请过此需求了！");
	
	public static final EBabyException BABY_TASK_NOT_OWNER = new EBabyException("10220006", "不是你的任务！");
	public static final EBabyException BABY_TASK_STATUS_ERROR = new EBabyException("10220007", "任务状态不正确，不能执行操作！");
	public static final EBabyException BABY_TASK_NOT_EXIST = new EBabyException("10220008", "没有此任务！");
	
	public static final EBabyException BABY_HAVE_EVALUTION_ERROR = new EBabyException("10220009", "你已评价过！不能重复评价");
	public static final EBabyException BABY_REQUIRE_NOT_OWNER = new EBabyException("10220010", "不是你的需求！");
	public static final EBabyException BABY_REQUIRE_HAVE_ARRIVEID = new EBabyException("10220011", "用户已到达，不能取消！");
	public static final EBabyException BABY_REQUIRE_HAVE_CANCELED = new EBabyException("10220012", "订单已被取消，不能重复执行！");
	public static final EBabyException INVITE_USER_NOT_EXIST = new EBabyException("10220013", "邀请人不存在，请确认手机号码是否正确！");
	
	public static final EBabyException SERVICE_NOT_EXIST = new EBabyException("10220014", "服务不存在！");
	public static final EBabyException USER_INFO_ERROR = new EBabyException("10220015", "用户信息错误！");
	public static final EBabyException PRICE_CANT_LESS_THAN_ZERO = new EBabyException("10220016", "价格不能小于零！");
	public static final EBabyException BABY_REQUIRE_CANT_BEEN_CANCEL = new EBabyException("10220017", "需求不能被取消，请联系客服处理");
	public static final EBabyException MUST_AFTER_PAYMENT = new EBabyException("10220018", "请先支付！");
}
