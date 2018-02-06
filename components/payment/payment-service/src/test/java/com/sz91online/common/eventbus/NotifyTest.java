package com.sz91online.common.eventbus;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sz91online.bgms.eventbus.MyEventBus;
import com.sz91online.bgms.module.payment.domain.BusiFinishNotifyBean;
import com.sz91online.bgms.module.payment.service.mybatis.MoneyEventHandler;
import com.sz91online.common.ServiceApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
public class NotifyTest {

	@Autowired
	private MyEventBus eventBus;
	
	@Autowired
	MoneyEventHandler handler;

	@Test
	public void notifyTest() {
		BusiFinishNotifyBean notifyBean = new BusiFinishNotifyBean();
		notifyBean.setAmount(0.01F);
		notifyBean.setBusiCode("BZJ123456789");
		notifyBean.setBusiType("WORK");
		notifyBean.setTime(new Date());
		notifyBean.setUserCode("user123456");
		notifyBean.setInviteUserCode("13312312312");

		eventBus.post(notifyBean);
		try {
			Thread.sleep(500000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//handler.sub(notifyBean);
	}
}
