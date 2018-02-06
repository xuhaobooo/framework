package com.sz91online.bgms.module.payment.dao;

import com.sz91online.bgms.module.payment.domain.PayUserBank;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface PayUserBankMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(PayUserBank record);

    PayUserBank selectByPrimaryKey(Long id);

    List<PayUserBank> selectAll();

    int updateByPrimaryKey(PayUserBank record);
}