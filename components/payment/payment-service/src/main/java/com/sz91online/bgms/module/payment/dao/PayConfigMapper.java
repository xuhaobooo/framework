package com.sz91online.bgms.module.payment.dao;

import com.sz91online.bgms.module.payment.domain.PayConfig;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface PayConfigMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(PayConfig record);

    PayConfig selectByPrimaryKey(Long id);

    List<PayConfig> selectAll();

    int updateByPrimaryKey(PayConfig record);
}