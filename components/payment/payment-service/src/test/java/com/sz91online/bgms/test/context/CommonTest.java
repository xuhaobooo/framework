package com.sz91online.bgms.test.context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.eventbus.AsyncEventBus;
import com.sz91online.common.ServiceApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ServiceApplication.class)
public class CommonTest {

	@Autowired
	private AsyncEventBus eventBus;
	
	@Test
	public void testPostEvent(){
		eventBus.post("test");
	}
	
}
