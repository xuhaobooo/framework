package com.sz91online.bgms.module.baby.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收费类型
 * 
 * @author zws
 * 
 */
public enum BabyRequireStatusEnum {

	NEW("待确认", "NEW"), CONFIRMED("已确认", "CONF"), ARRIVED("已到达", "ARRV"), PAID("已存保证金", "PAID"), PROVIDE_FINISHED("服务已完成",
			"PF"), CUSTOMER_FINISHED("用户确认已完成", "CF"),FINISHED("订单已完成","AF"),CANCEL("已取消","CC");

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private String value;

	private BabyRequireStatusEnum(String desc, String value) {
		this.value = value;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static BabyRequireStatusEnum getEnum(String value) {
		BabyRequireStatusEnum resultEnum = null;
		BabyRequireStatusEnum[] enumAry = BabyRequireStatusEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue().equals(value)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		BabyRequireStatusEnum[] ary = BabyRequireStatusEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", String.valueOf(ary[num].getValue()));
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		BabyRequireStatusEnum[] ary = BabyRequireStatusEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
}
