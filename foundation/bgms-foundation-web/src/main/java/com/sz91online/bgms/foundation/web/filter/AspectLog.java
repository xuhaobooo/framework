package com.sz91online.bgms.foundation.web.filter;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AspectLog {
	Logger log = LoggerFactory.getLogger(AspectLog.class);

	// 在类里面写方法，方法名诗可以任意的。此处我用标准的before和after来表示
	// 此处的JoinPoint类可以获取，action所有的相关配置信息和request等内置对象。
	public void before(JoinPoint joinpoint) {
		// 此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象
		log.debug("开始调用后台接口方法：" + joinpoint.getTarget().getClass() + "."
				+ joinpoint.getSignature().getName());
		Object[] object = joinpoint.getArgs();
		for (Object o : object) {
			log.debug(o.toString());
		}
		
	}

	public void after(JoinPoint joinpoint) {
		log.debug("结束调用后台接口方法：" + joinpoint.getTarget().getClass() + "."
				+ joinpoint.getSignature().getName());
	}
}
