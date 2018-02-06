package com.sz91online.bgms.module.payment.dao;

import com.sz91online.bgms.module.payment.domain.PayOrder;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface PayOrderMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(PayOrder record);

    PayOrder selectByPrimaryKey(Long id);

    List<PayOrder> selectAll();

    int updateByPrimaryKey(PayOrder record);
}