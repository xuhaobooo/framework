package com.sz91online.bgms.module.baby.dao;

import com.sz91online.bgms.module.baby.domain.BabyInvite;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface BabyInviteMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(BabyInvite record);

    BabyInvite selectByPrimaryKey(Long id);

    List<BabyInvite> selectAll();

    int updateByPrimaryKey(BabyInvite record);
}