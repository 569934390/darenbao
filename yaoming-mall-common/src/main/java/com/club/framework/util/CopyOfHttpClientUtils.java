//package com.club.framework.util;
// 
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
// 
//import org.apache.commons.io.IOUtils;
//import org.apache.http.HeaderIterator;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpStatus;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.config.CookieSpecs;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
// 
//public class CopyOfHttpClientUtils {
//	private static RequestConfig requestconfig = RequestConfig.custom().setSocketTimeout(5000)
//	        									 .setConnectTimeout(5000).setCookieSpec(CookieSpecs.BEST_MATCH).build();
//	
//	public static List<String> post(String url,String postParmas){
//	    CloseableHttpClient httpclient = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost(url);
//        httpPost.setConfig(requestconfig);
//        CloseableHttpResponse response=null;
//        HttpEntity entity=null;
//        try {
//			response =httpclient.execute(httpPost);
//			int statusCode = response.getStatusLine().getStatusCode();
//	        if(statusCode != HttpStatus.SC_OK) {
//	            System.err.println("Method failed: " + response.getStatusLine());
//	        }
//	        entity = response.getEntity();
//	        List<String> lines=IOUtils.readLines(entity.getContent());
//	        return lines;
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally{
//			try {
//				EntityUtils.consume(entity);
//				response.close();
//				httpclient.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}
//    public static void main(String[] args) {   
//    CloseableHttpClient httpclient = HttpClients.createDefault();
//    HttpGet httpget = new HttpGet("http://www.soso.com");
//    CloseableHttpResponse response = null;
//         
//    // 设置请求和传输超时时间5s,设置cookie策略
//    RequestConfig requestconfig = RequestConfig.custom().setSocketTimeout(5000)
//        .setConnectTimeout(5000).setCookieSpec(CookieSpecs.BEST_MATCH).build();
//    httpget.setConfig(requestconfig);
//         
//    System.out.println(httpget.getURI());
//    // 打印请求信息
//    System.out.println("Executing request " + httpget.getRequestLine());
//    System.out.println("------------------------------");
//         
//    try {
//        response = httpclient.execute(httpget);
//             
//            System.out.println(response.getStatusLine().toString());
//            System.out.println("------------------------------");
//             
//        // 头信息
//        HeaderIterator it = response.headerIterator();
//        while(it.hasNext()) {
//            System.out.println(it.next());
//        }
//        System.out.println("------------------------------");
//             
//        // 判断访问的状态码
//        int statusCode = response.getStatusLine().getStatusCode();
//        if(statusCode != HttpStatus.SC_OK) {
//            System.err.println("Method failed: " + response.getStatusLine());
//        }
////      if(statusCode == HttpStatus.SC_OK) {
////      
////      } else if((statusCode == HttpStatus.SC_MOVED_TEMPORARILY) 
////                  || (statusCode == HttpStatus.SC_MOVED_PERMANENTLY) 
////                  || (statusCode == HttpStatus.SC_SEE_OTHER)
////                  || (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
////          //页面重定向代码
////      }
//             
//        HttpEntity entity = response.getEntity();
//        StringBuilder pageBuffer = new StringBuilder();
//        if(entity != null) {
//        InputStream in = entity.getContent();
//        BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
//        String line;
//        while((line = br.readLine()) != null) {
//            pageBuffer.append(line);
//            pageBuffer.append("\n");
//        }
//        System.out.println(pageBuffer.toString());
//        in.close();
//        br.close();
//        }
//             
//        System.out.println("------------------------------");
//             
//        HttpPost httpPost = new HttpPost("http://www.weibo.com");
//             
//        // 将要POST的数据封包
//        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//        nvps.add(new BasicNameValuePair("username", "vip"));
//        nvps.add(new BasicNameValuePair("password", "123456"));
//        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
//         
//        CloseableHttpResponse response1 = httpclient.execute(httpPost);
//        try {
//            HttpEntity entity1 = response1.getEntity();
//            if(entity != null) {
//                System.out.println(EntityUtils.toString(entity1,"UTF-8"));
//
//        }
//            EntityUtils.consume(entity1);
//        } finally {
//            response.close();
//        }
//             
//    } catch(ClientProtocolException e) {
//        e.printStackTrace();
//    } catch(IOException e) {
//        e.printStackTrace();
//    } finally {
//        try {
//            response.close();
//            httpclient.close();
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//        }
//    }
//}
