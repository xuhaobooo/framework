package com.sz91online.bgms.module.payment.dao;

import com.sz91online.bgms.module.payment.domain.PayPayment;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface PayPaymentMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(PayPayment record);

    PayPayment selectByPrimaryKey(Long id);

    List<PayPayment> selectAll();

    int updateByPrimaryKey(PayPayment record);
}