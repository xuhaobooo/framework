package com.sz91online.bgms.module.payment.dao;

import com.sz91online.bgms.module.payment.domain.PayWithdraw;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface PayWithdrawMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(PayWithdraw record);

    PayWithdraw selectByPrimaryKey(Long id);

    List<PayWithdraw> selectAll();

    int updateByPrimaryKey(PayWithdraw record);
}