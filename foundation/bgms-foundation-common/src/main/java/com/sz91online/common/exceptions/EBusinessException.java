package com.sz91online.common.exceptions;

/**
 * 业务异常基类，所有业务异常都必须继承于此异常
 * 
 * 定义异常时，需要先确定异常所属模块。例如：血糖添加 报错 可以定义为 [10020001] 前四位数为系统模块编号，后4位为错误代码 ,唯一 <br>
 * 用户模块异常 1001 <br>
 * 血糖模块异常 1002 <br>
 * 套餐模块异常 1003 <br>
 * 支付模块导演  1004 <br>
 * 
 */

public class EBusinessException extends RuntimeException {
	
	private static final long serialVersionUID = -5875371379845226068L;

	/**
	 * 数据库操作,insert返回0
	 */
	public static final EBusinessException DB_INSERT_RESULT_0 = new EBusinessException("90040001", "数据库操作,insert返回0");

	/**
	 * 数据库操作,update返回0
	 */
	public static final EBusinessException DB_UPDATE_RESULT_0 = new EBusinessException("90040002", "数据库操作,update返回0");

	/**
	 * 数据库操作,selectOne返回null
	 */
	public static final EBusinessException DB_SELECTONE_IS_NULL = new EBusinessException("90040003", "数据库操作,selectOne返回null");

	/**
	 * 数据库操作,list返回null
	 */
	public static final EBusinessException DB_SELECTONE_IS_MANY = new EBusinessException("90040004", "数据库操作,selectOne返回多值");

	public static final EBusinessException DB_RECORD_EXIST = new EBusinessException("90040004", "数据库操作,记录已存在");
	public static final EBusinessException DB_RECORD_NOT_EXIST = new EBusinessException("90040005", "数据库操作,记录不存在");
	
	/**
	 * 会话超时 获取session时，如果是空，throws 下面这个异常 拦截器会拦截爆会话超时页面
	 */
	public static final EBusinessException SESSION_IS_OUT_TIME = new EBusinessException("90040006", "会话超时");
	
	public static final EBusinessException MIS_PARAMETER_ERROR = new EBusinessException("90040007","缺少必要参数");
	public static final EBusinessException UNEXPECT_PARAMETER_ERROR = new EBusinessException("90040008","不期待参数");
	public static final EBusinessException ASC_DESC_ERROR = new EBusinessException("90040009","该参数只能为desc或asc");
	
	public static final EBusinessException TOKEN_IS_ILLICIT = new EBusinessException("90040010", "Token 验证非法");
	public static final EBusinessException CREATE_OBJ_ERROR = new EBusinessException("90040012","反射生成对象失败");
	
	public static final EBusinessException BUSINESS_PENDING = new EBusinessException("90040013","业务暂未实现！");

	public static final EBusinessException UPDATE_MISS_ID = new EBusinessException("90040014","更新记录时没有主键参数");;

	protected String resultCode;
	protected String resultMsg;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public EBusinessException(String resultCode, String resultMsg) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}
	
	public EBusinessException(Exception e) {
		this.resultCode = "-1";
		this.resultMsg = e.getMessage();
	}
}
