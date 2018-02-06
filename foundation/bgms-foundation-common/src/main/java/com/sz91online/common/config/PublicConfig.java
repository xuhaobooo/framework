/**
 * className：PublicConifg.java <br>
 * @version：1.0  <br>
 * date: 2014-11-5-上午10:15:20     <br>
 */
package com.sz91online.common.config;

import java.util.Map;

import com.sz91online.common.utils.PlResourceUtils;


/**
 * 环境配置基础类 <br>
 * 
 */
public class PublicConfig {

	/**
	 * 系统文件配置 加载。
	 */
	public static Map<String, String> PUBLIC_USER = PlResourceUtils.getResource("public_user").getMap();

	/**
	 * 系统文件配置 加载。
	 */
	public static Map<String, String> PUBLIC_SYSTEM = PlResourceUtils.getResource("public_system").getMap();

	/**
	 * 通知URL
	 */
	public final static String NOTIFY_RECEIVE_URL = PUBLIC_SYSTEM.get("NOTIFY_RECEIVE_URL");

}
