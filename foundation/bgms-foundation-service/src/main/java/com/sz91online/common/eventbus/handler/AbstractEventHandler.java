package com.sz91online.common.eventbus.handler;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.sz91online.bgms.eventbus.MyEventBus;

public abstract class AbstractEventHandler {

	@Autowired
	private MyEventBus eventBus;

	@PostConstruct
	public void postConstruct() {
		eventBus.register(this);
	}
}
