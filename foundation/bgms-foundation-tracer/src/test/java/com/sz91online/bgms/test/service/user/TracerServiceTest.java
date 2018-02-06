package com.sz91online.bgms.test.service.user;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sz91online.bgms.module.tracer.domain.Tracer;
import com.sz91online.bgms.module.tracer.service.TracerService;
import com.sz91online.bgms.module.tracer.util.TracerConstants;
import com.sz91online.common.ServiceApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ServiceApplication.class)
@Transactional
public class TracerServiceTest {

	@Autowired
	private TracerService tracerService;
	
	@Test
	@Rollback(true)
	public void testSaveTracer(){
		Tracer tracer = new Tracer();
		tracer.setAction(TracerConstants.ACTION_CREATE);
		tracer.setBusinessId(1L);
		tracer.setContent("junit test");
		tracer.setCreatedTime(new Date());
		tracer.setCreatedUser("tester");
		tracer.setExtraId(null);
		tracer.setModule("CRM");
		tracer.setSubModule("Crm-test");
		tracerService.save(tracer);
	}
	
	//@Test
	@Rollback(true)
	public void testTracerApect(){
		Tracer tracer = new Tracer();
		tracer.setAction(TracerConstants.ACTION_CREATE);
		tracer.setBusinessId(1L);
		tracer.setContent("junit test");
		tracer.setCreatedTime(new Date());
		tracer.setCreatedUser("tester");
		tracer.setExtraId(null);
		tracer.setModule("CRM");
		tracer.setSubModule("Crm-test");
		tracerService.saveWithSession(tracer, "test");
	}
}
