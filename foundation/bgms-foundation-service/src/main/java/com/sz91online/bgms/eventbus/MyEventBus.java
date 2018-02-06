package com.sz91online.bgms.eventbus;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

import com.google.common.eventbus.AsyncEventBus;

@Component
public class MyEventBus extends AsyncEventBus{

	public MyEventBus(){
		super(Executors.newFixedThreadPool(30));
	}
	
	public MyEventBus(Executor executor) {
		super(executor);
	}

}
