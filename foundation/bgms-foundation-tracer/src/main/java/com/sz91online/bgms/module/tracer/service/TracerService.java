package com.sz91online.bgms.module.tracer.service;

import com.sz91online.bgms.module.tracer.domain.Tracer;
import com.sz91online.common.db.service.IDefaultService;

public interface TracerService extends IDefaultService<Tracer> {

    Long save(Tracer activityStream);
}
