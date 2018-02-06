package com.sz91online.bgms.module.baby.dao;

import com.sz91online.bgms.module.baby.domain.BabyTimePrice;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface BabyTimePriceMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(BabyTimePrice record);

    BabyTimePrice selectByPrimaryKey(Long id);

    List<BabyTimePrice> selectAll();

    int updateByPrimaryKey(BabyTimePrice record);
}