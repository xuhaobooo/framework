package com.sz91online.alipay;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sz91online.common.ServiceApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ServiceApplication.class)
@Transactional
public class PaymentServiceTest {
	
	@Test
	public void testVerify() {
		
	}
}
