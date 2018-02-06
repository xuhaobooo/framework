package com.sz91online.bgms.module.user.dao;

import com.sz91online.bgms.module.user.domain.Captcha;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface CaptchaMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(Captcha record);

    Captcha selectByPrimaryKey(Long id);

    List<Captcha> selectAll();

    int updateByPrimaryKey(Captcha record);
}