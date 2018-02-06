package com.sz91online.bgms.module.payment.enums;

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
public enum BusiTypeEnum {
	
	BZJ("保证金", "BZJ"), RC("业务收入", "RC"), TH("退货收入", "TH"), TX("提现", "TX");

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private String value;

	private BusiTypeEnum(String desc, String value) {
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

	public static BusiTypeEnum getEnum(String value) {
		BusiTypeEnum resultEnum = null;
		BusiTypeEnum[] enumAry = BusiTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue().equals(value)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, String>> toMap() {
		BusiTypeEnum[] ary = BusiTypeEnum.values();
		Map<String, Map<String, String>> enumMap = new HashMap<String, Map<String, String>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, String> map = new HashMap<String, String>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", String.valueOf(ary[num].getValue()));
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		BusiTypeEnum[] ary = BusiTypeEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
	
	public static void main(String[] args) {
		Map<String, Map<String, String>> map = toMap();
		System.out.println(map);
	}
}
