package com.sz91online.bgms.module.payment.dao;

import com.sz91online.bgms.module.payment.domain.PayMoneyFlow;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface PayMoneyFlowMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(PayMoneyFlow record);

    PayMoneyFlow selectByPrimaryKey(Long id);

    List<PayMoneyFlow> selectAll();

    int updateByPrimaryKey(PayMoneyFlow record);
}