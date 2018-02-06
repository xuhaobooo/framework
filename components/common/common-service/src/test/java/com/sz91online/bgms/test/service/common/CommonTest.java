package com.sz91online.bgms.test.service.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.sz91online.common.ServiceApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ServiceApplication.class)
public class CommonTest {

	@Test
	public void testPostEvent(){
		Assert.isTrue(true,"");
	}
	
}
