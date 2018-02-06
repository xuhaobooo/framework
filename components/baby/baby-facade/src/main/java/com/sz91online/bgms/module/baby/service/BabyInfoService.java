package com.sz91online.bgms.module.baby.service;

import java.util.List;

import com.sz91online.bgms.module.baby.domain.BabyInfo;
import com.sz91online.common.db.service.IDefaultService;

public interface BabyInfoService extends IDefaultService<BabyInfo>{

	//添加用户的宝贝
	public void addBabyToUser(String userCode, String babyCode);
	
	//删除用户宝贝
	public void removeBabyFromUser(String userCode, String babyCode);

	//通过用户编号查询所有宝贝
	public List<BabyInfo> findByUserCode(String code);
	
}
