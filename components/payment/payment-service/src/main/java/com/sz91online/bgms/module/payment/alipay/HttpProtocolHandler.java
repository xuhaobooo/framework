package com.sz91online.bgms.module.payment.alipay;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;

import com.sz91online.bgms.module.payment.exceptions.EPaymentException;
import com.sz91online.common.utils.PlStringUtils;

/**
 * Created by wendajun on 2016/4/20. httpClient
 */
public class HttpProtocolHandler {

	private static String DEFAULT_CHARSET = "UTF-8";

	/** 连接超时时间，由bean factory设置，缺省为8秒钟 */
	private int defaultConnectionTimeout = 8000;

	/** 回应超时时间, 由bean factory设置，缺省为30秒钟 */
	private int defaultSoTimeout = 30000;

	private static HttpProtocolHandler httpProtocolHandler = new HttpProtocolHandler();

	/**
	 * 工厂方法
	 * 
	 * @return
	 */
	public static HttpProtocolHandler getInstance() {
		return httpProtocolHandler;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	private HttpResponse baseExecute(HttpRequest request)
			throws UnsupportedEncodingException, FileNotFoundException {
		
		Assert.notNull(request, "参数对象不能为空");
		Assert.notNull(request.getUrl(), "请求路径不能为空");
		Assert.notNull(request.getMethod(), "请求方式不能为空");
		
		CloseableHttpClient httpclient = HttpClients.createDefault();

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(defaultConnectionTimeout)
				.setConnectionRequestTimeout(3000).setSocketTimeout(defaultSoTimeout).build();

		String charset = request.getCharset();
		charset = charset == null ? DEFAULT_CHARSET : charset;

		HttpRequestBase method;

		// get模式且不带上传文件
		if (request.getMethod().equals(HttpRequest.METHOD_GET)) {
			String url = request.getUrl();
			// parseNotifyConfig会保证使用GET方法时，request一定使用QueryString
			if (request.getQueryString() != null) {
				url = url + "?" + request.getQueryString();
			}
			method = new HttpGet(url);
			method.setConfig(requestConfig);

		} else {
			// post模式且不带上传文件
			method = new HttpPost(request.getUrl());
			if (PlStringUtils.isNotEmpty(request.getRequestXml())) {
				((HttpPost) method).setEntity(new StringEntity(request.getRequestXml(), "utf-8"));
			} else if (request.getParameters() != null) {
				((HttpPost) method).setEntity(new UrlEncodedFormEntity(request.getParameters()));
			} else {
				throw EPaymentException.MIS_PARAMETER_ERROR;
			}
			method.addHeader("Content-Type", "application/x-www-form-urlencoded; text/html; charset=" + charset);
		}

		// 设置Http Header中的User-Agent属性
		method.addHeader("User-Agent", "Mozilla/4.0");
		HttpResponse response = new HttpResponse();

		try {
			CloseableHttpResponse res = httpclient.execute(method);
			if (request.getResultType().equals(HttpResultType.STRING)) {
				response.setStringResult(new String(EntityUtils.toString(res.getEntity(), "UTF-8")));
			} else if (request.getResultType().equals(HttpResultType.BYTES)) {
				response.setByteResult(EntityUtils.toByteArray(res.getEntity()));
			}
			response.setResponseHeaders(res.getAllHeaders());
		} catch (UnknownHostException ex) {

			return null;
		} catch (IOException ex) {

			return null;
		} catch (Exception ex) {

			return null;
		} finally {
			method.releaseConnection();
		}
		return response;
	}

	/**
	 * 执行Http请求
	 *
	 * @param request
	 *            请求数据
	 * @return
	 * @throws HttpException,
	 *             IOException
	 */
	public HttpResponse execute(HttpRequest request) throws HttpException, IOException {
		return baseExecute(request);
	}

	/**
	 * 将NameValuePairs数组转变为字符串
	 * 
	 * @param nameValues
	 * @return
	 */
	protected String toString(NameValuePair[] nameValues) {
		if (nameValues == null || nameValues.length == 0) {
			return "null";
		}

		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < nameValues.length; i++) {
			NameValuePair nameValue = nameValues[i];

			if (i == 0) {
				buffer.append(nameValue.getName() + "=" + nameValue.getValue());
			} else {
				buffer.append("&" + nameValue.getName() + "=" + nameValue.getValue());
			}
		}

		return buffer.toString();
	}

	public static void main(String[] args) {

	}
}
