package com.sz91online.bgms.module.payment.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sz91online.bgms.module.payment.domain.SimplePayOrder;
import com.sz91online.common.db.service.ISearchableDAO;

public interface PayOrderMapperExt extends ISearchableDAO {

	List<SimplePayOrder> findMine(@Param(value = "user_code") String userCode, @Param(value = "start_time") Date startTime,
			@Param(value = "end_time") Date endTime);
}