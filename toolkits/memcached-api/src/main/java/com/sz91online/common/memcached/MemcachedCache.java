package com.sz91online.common.memcached;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import com.google.code.ssm.api.format.SerializationType;
import com.google.code.ssm.providers.CacheException;

public class MemcachedCache implements Cache {
	private com.google.code.ssm.Cache cache;

	public com.google.code.ssm.Cache getCache() {
		return cache;
	}

	public void setCache(com.google.code.ssm.Cache cache) {
		this.cache = cache;
	}

	@Override
	public String getName() {
		return this.cache.getName();
	}

	@Override
	public Object getNativeCache() {
		return this.cache;
	}

	@Override
	public ValueWrapper get(Object key) {
		Object object = null;
		try {
			//this.cache.delete((String)key); 
			object = this.cache.get(key.toString(), SerializationType.JAVA);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (CacheException e) {
			e.printStackTrace();
		}
		return (object != null ? new SimpleValueWrapper(object) : null);
	}

	@Override
	public void put(Object key, Object value) {
		try {
			this.cache.set((String) key, 86400, value, SerializationType.JAVA);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void evict(Object key) {
		try {
			this.cache.delete((String) key);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void clear() {
		try {
			this.cache.flush();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		Object object = null;
		try {
			object = this.cache.get((String) key, SerializationType.JAVA);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (CacheException e) {
			e.printStackTrace();
		}
		return (object != null ? (T) object : null);
	}

	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		Object object = null;
		try {
			object = this.cache.get((String) key, SerializationType.JAVA);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (CacheException e) {
			e.printStackTrace();
		}
		return (object != null ? (T) object : null);
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		ValueWrapper existingValue = this.get(key);
		if (existingValue == null) {
			this.put(key, value);
			return null;
		} else {
			return existingValue;
		}
	}
}
