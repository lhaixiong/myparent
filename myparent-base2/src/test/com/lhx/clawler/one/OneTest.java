package com.lhx.clawler.one;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.junit.Test;

import java.io.IOException;

public class OneTest {
	@Test
	public void test02() throws IOException {
		HttpClient client = new HttpClient();
		// 得到post 方法
		PostMethod postMethod = new PostMethod("http://www.saybot.com/postme");
		// 使用数组来传递参数
		NameValuePair[] postData = new NameValuePair[2];
		// 设置参数
		postData[0] = new NameValuePair("武器", "枪");
		postData[1] = new NameValuePair("什么枪", "神枪");
		postMethod.addParameters(postData);
		// 回车，获得响应状态码
		int statusCode = client.executeMethod(postMethod);
		// 查看命中情况，可以获得的东西还有很多，比如head、cookies 等
        System.out.println("status:" + statusCode + ",response:"
                + postMethod.getResponseBodyAsString());
		// 释放
		postMethod.releaseConnection();
	}

	@Test
	public void test01() throws IOException {
		// 创建一个客户端，类似于打开一个浏览器
		HttpClient client = new HttpClient();

		// 创建一个get 方法，类似于在浏览器地址栏中输入一个地址
		GetMethod getMethod = new GetMethod("http://www.baidu.com");

		HttpClientParams params = new HttpClientParams();
		params.setContentCharset("utf-8");
		getMethod.setParams(params);
		// 返回乱码,下面处理不生效，why?
		// getMethod.setRequestHeader("Content-Type",
		// "application/x-www-form-urlencoded; charset=utf-8");
		// getMethod.setRequestHeader("Accept", "text/plain;charset=utf-8");

		// 回车，获得响应状态码
		int statusCode = client.executeMethod(getMethod);

		// 查看命中情况，可以获得的东西还有很多，比如head、cookies 等
		System.out.println("status:" + statusCode + ",response:"
				+ getMethod.getResponseBodyAsString());

		// 释放
		getMethod.releaseConnection();
	}
}
