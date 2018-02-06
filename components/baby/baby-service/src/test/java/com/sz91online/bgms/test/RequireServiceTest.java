package com.sz91online.bgms.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sz91online.bgms.eventbus.MyEventBus;
import com.sz91online.bgms.module.baby.domain.BabyRequire;
import com.sz91online.bgms.module.baby.service.BabyRequireService;
import com.sz91online.bgms.module.payment.domain.PayOrder;
import com.sz91online.common.ServiceApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ServiceApplication.class)
@Transactional
public class RequireServiceTest {

	@Autowired
	private BabyRequireService babyRequireService;
	
	@Autowired
	private MyEventBus eventBus;

	@Test
	public void testGetSubDept() {
		List<BabyRequire> deptList = babyRequireService.findAll();
		Assert.isTrue(0==deptList.size(),"DEPT1 的子部门数正确");
	}

	//@Test
	public void payNotify() {
		eventBus.post(new PayOrder());
	}
}
