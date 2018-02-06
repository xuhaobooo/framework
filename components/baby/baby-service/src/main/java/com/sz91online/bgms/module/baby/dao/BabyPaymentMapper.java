package com.sz91online.bgms.module.baby.dao;

import com.sz91online.bgms.module.baby.domain.BabyPayment;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface BabyPaymentMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(BabyPayment record);

    BabyPayment selectByPrimaryKey(Long id);

    List<BabyPayment> selectAll();

    int updateByPrimaryKey(BabyPayment record);
}