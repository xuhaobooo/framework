package com.sz91online.bgms.test.service.user;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sz91online.bgms.module.user.domain.Dept;
import com.sz91online.bgms.module.user.service.DeptService;
import com.sz91online.common.ServiceApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ServiceApplication.class)
@Transactional
public class DeptServiceTest {

	@Autowired
	private DeptService deptService;

	@Test
	public void testGetSubDept() {
		List<Dept> deptList = deptService.getSubDeptByDeptId("DEPT1");
		Assert.isTrue(2==deptList.size(),"DEPT1 的子部门数正确");
		
		deptService.getSubDeptByDeptId("DEPT2");
		deptService.getSubDeptByDeptId("DEPT1");
	}

}
