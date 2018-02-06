package com.sz91online.bgms.module.payment.dao;

import com.sz91online.bgms.module.payment.domain.PayBalance;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface PayBalanceMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(PayBalance record);

    PayBalance selectByPrimaryKey(Long id);

    List<PayBalance> selectAll();

    int updateByPrimaryKey(PayBalance record);
}