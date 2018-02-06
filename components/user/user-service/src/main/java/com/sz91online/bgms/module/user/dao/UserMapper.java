package com.sz91online.bgms.module.user.dao;

import com.sz91online.bgms.module.user.domain.User;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface UserMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
}