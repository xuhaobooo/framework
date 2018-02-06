package com.sz91online.bgms.module.tracer.service.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.tracer.dao.TracerMapper;
import com.sz91online.bgms.module.tracer.dao.TracerMapperExt;
import com.sz91online.bgms.module.tracer.domain.Tracer;
import com.sz91online.bgms.module.tracer.service.TracerService;
import com.sz91online.bgms.module.tracer.util.ClassInfo;
import com.sz91online.bgms.module.tracer.util.ClassInfoMap;
import com.sz91online.bgms.module.tracer.util.Traceable;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
@Traceable(nameField = "content")
public class TracerServiceImpl extends DefaultSearchService<Tracer> implements TracerService {
	//注册class信息，如果此类需要作用户操作记录，须添加下面代码
	static {
		ClassInfoMap.put(TracerServiceImpl.class, new ClassInfo("Tracer", "Tracer-test"));//ModuleNameConstants.CRM, CrmTypeConstants.ACCOUNT));
	}

	@Autowired
	private TracerMapper tracerMapper;

	@Autowired
	private TracerMapperExt tracerMapperExt;

	@Override
	public Long save(Tracer tracer) {
		tracerMapper.insertAndReturnKey(tracer);
		return tracer.getId();

	}

	@Override
	public ISearchableDAO getSearchMapper() {
		return tracerMapperExt;
	}

	@Override
	public ICrudGenericDAO<Tracer> getCrudMapper() {
		return tracerMapper;
	}

}
