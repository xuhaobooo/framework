package com.sz91online.bgms.module.tracer.dao;

import com.sz91online.bgms.module.tracer.domain.Tracer;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface TracerMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(Tracer record);

    Tracer selectByPrimaryKey(Long id);

    List<Tracer> selectAll();

    int updateByPrimaryKey(Tracer record);
}