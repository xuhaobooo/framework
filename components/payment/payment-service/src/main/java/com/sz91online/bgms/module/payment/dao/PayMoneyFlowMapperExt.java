package com.sz91online.bgms.module.payment.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sz91online.bgms.module.payment.domain.SimplePayMoneyFlow;
import com.sz91online.common.db.service.ISearchableDAO;

public interface PayMoneyFlowMapperExt extends ISearchableDAO {

	List<SimplePayMoneyFlow> findMine(@Param(value = "user_code") String code,
			@Param(value = "start_time") Date startTime, @Param(value = "end_time") Date endDate);
}