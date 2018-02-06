package com.bgms.sz91online.common.memcached.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:sz91online-*.xml" })
public class MemcachedReadTest {

	@Autowired
	private SimpleCacheManager manager;
	
	@Test
	public void testContext() throws Exception {

		Cache cache = manager.getCache("memoryMapCache");
		cache.put("test", "This is a test string!");
		System.out.println("result:"+ cache.get("test").get());
		
/*		Cache cache2 = manager.getCache("default");
		cache2.put("test2", "This is a test string!");
		System.out.println("result:"+ cache2.get("test2").get());*/
	}

}
