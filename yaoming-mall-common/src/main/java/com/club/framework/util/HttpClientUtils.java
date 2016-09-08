package com.club.framework.util;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;

import com.club.framework.exception.BaseAppException;

public class HttpClientUtils {
	private static RequestConfig requestconfig = RequestConfig.custom()
			.setSocketTimeout(5000).setConnectTimeout(5000)
			.setCookieSpec(CookieSpecs.BEST_MATCH).build();

	public static String post(String url,Map<String,Object> paramMap) throws BaseAppException {
		StringBuffer sb = new StringBuffer();
		if (paramMap.isEmpty()) {
		}else {
			for (String key : paramMap.keySet()) {
				if(paramMap.get(key)==null||paramMap.get(key).toString().isEmpty()) continue;
				String value =paramMap.get(key).toString();
				if (sb.length() < 1) {
					sb.append(key).append("=").append(value);
				}
				else {
					sb.append("&").append(key).append("=").append(value);
				}
			}
		}
		return post(url,sb.toString());
	}
	public static String get(String url,Map<String,Object> paramMap) throws BaseAppException {
		StringBuffer sb = new StringBuffer();
		if (paramMap.isEmpty()) {
		}else {
			for (String key : paramMap.keySet()) {
				if(paramMap.get(key)==null||paramMap.get(key).toString().isEmpty()) continue;
				String value = paramMap.get(key).toString();
				if (sb.length() < 1) {
					sb.append(key).append("=").append(value);
				}
				else {
					sb.append("&").append(key).append("=").append(value);
				}
			}
		}
		String newUrl="";
		if(url.indexOf("?")==-1){
			newUrl=url+"?"+sb.toString();
		}else{
			newUrl=url+"&"+sb.toString();
		}
		return get(newUrl);
	}

	public static String post(String url, String postParmas) throws BaseAppException {
		CloseableHttpClient httpclient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestconfig);
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		String[] paramsArr=postParmas.split("&");
//		for (String string : paramsArr) {
//			String[] tempArr=string.split("=");
//            params.add(new BasicNameValuePair(tempArr[0],tempArr[1]));
//		}
		HttpEntity requestEntity = null;
		CloseableHttpResponse response = null;
		HttpEntity responseEntity = null;
		try {
			requestEntity=new StringEntity(postParmas,ContentType.create("application/x-www-form-urlencoded", Consts.UTF_8));
			httpPost.setEntity(requestEntity);
			response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + response.getStatusLine());
				throw new BaseAppException("test", "获取数据失败");
			}
			responseEntity = response.getEntity();
			List<String> lines = IOUtils.readLines(responseEntity.getContent(), Consts.UTF_8);
			StringBuffer buffer=new StringBuffer();
			for (String string : lines) {
				buffer.append(string+"\r");
			}
			return buffer.toString();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				EntityUtils.consume(requestEntity);
				EntityUtils.consume(responseEntity);
				if(response!=null){
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String get(String url) throws BaseAppException {
		CloseableHttpClient httpclient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
		HttpGet httpPost = new HttpGet(url);
		httpPost.setConfig(requestconfig);
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		String[] paramsArr=postParmas.split("&");
//		for (String string : paramsArr) {
//			String[] tempArr=string.split("=");
//            params.add(new BasicNameValuePair(tempArr[0],tempArr[1]));
//		}
		CloseableHttpResponse response = null;
		HttpEntity responseEntity = null;
		try {
			response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + response.getStatusLine());
				throw new BaseAppException("test", "获取数据失败");
			}
			responseEntity = response.getEntity();
			List<String> lines = IOUtils.readLines(responseEntity.getContent(),Consts.UTF_8);
			StringBuffer buffer=new StringBuffer();
			for (String string : lines) {
				buffer.append(string+"\r");
			}
			return buffer.toString();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				EntityUtils.consume(responseEntity);
				if(response!=null){
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws BaseAppException {
//		String response=HttpClientUtils.post("http://localhost:28080/base/getList.do","tableName=sub_sys_conf");
//		String response=HttpClientUtils.get("http://localhost:28080/common/jslibs/anychart/index.html");
		String response=HttpClientUtils.get("http://www.baidu.com/s?wd=java&rsv_spt=1&issp=1&f=8&rsv_bp=0&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=0&rsv_pq=f8c028cc00014c7f&rsv_t=36dfBS3lKuDcP3hiVgjlBKskGo2qL8xa2RvBT%2FeK8EnA0nlAW3UY9Hb0voPn%2BFd1wUCQ&inputT=3880&oq=http%20url&sug=httpurlconnection&rsv_sug=2");
		System.out.println(response);
	}
}
