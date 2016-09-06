package com.club.framework.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.framework.exception.SysRuntimeException;

/**
 * <Description> Http工具类<br>
 */
public class HttpUtils {

    public enum HttpMethod {
        GET, POST, DELETE, PUT
    }

    private HttpURLConnection urlc = null;

    private String encoding = FrameWorkConstants.UTF_8_ENCODING;

    private HttpMethod method = HttpMethod.GET;

    private int timeout = 2000;

    private Map<String, String> properties = new HashMap<String, String>();

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void addProperties(String key, String value) {
        if (properties.containsKey(key)) {
            properties.remove(key);
        }
        properties.put(key, value);
    }

    private String prepareParam(Map<String, Object> paramMap) {
        StringBuffer sb = new StringBuffer();
        if (paramMap.isEmpty()) {
            return "";
        }
        else {
            for (String key : paramMap.keySet()) {
                String value = (String) paramMap.get(key);
                if (sb.length() < 1) {
                    sb.append(key).append("=").append(value);
                }
                else {
                    sb.append("&").append(key).append("=").append(value);
                }
            }
            return sb.toString();
        }
    }

    /**
     * 发送请求
     * 
     * @param urlStr
     * @param paramMap
     * @throws Exception
     */
    public void sendRequest(String urlStr, Map<String, Object> paramMap)
            throws Exception {
        String paramStr = prepareParam(paramMap);
        if (paramStr == null || paramStr.trim().length() < 1) {

        }
        else {
            urlStr += "?" + paramStr;
        }
        sendRequest(urlStr, "");
    }

    public void sendRequest(String url, byte[] data) {
        try {
            if (method.equals(HttpMethod.GET)) {
                StringBuffer param = new StringBuffer();
                int i = 0;
                for (String key : properties.keySet()) {
                    if (i == 0) {
                        param.append("?");
                    }
                    else {
                        param.append("&");
                    }
                    param.append(key).append("=").append(properties.get(key));
                    i++;
                }
                url += param;
            }

            URL u = new URL(url);
            urlc = (HttpURLConnection) u.openConnection();
            urlc.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            urlc.setReadTimeout(timeout);
            urlc.addRequestProperty("method", String.valueOf(method));
            urlc.setDoOutput(true);
            urlc.setDoInput(true);
            urlc.setUseCaches(false);

            for (String key : properties.keySet()) {
                urlc.addRequestProperty(key, properties.get(key));
            }

            if (method.equals(HttpMethod.POST)) {
                urlc.getOutputStream().write(data);
            }

        }
        catch (Throwable e) {
            throw new SysRuntimeException(e, "HttpUtil发送http请求出现异常"
                    + e.getMessage());
        }

    }

    public void sendRequest(String url, String data) {
        byte[] sdata = null;
        try {
            sdata = data.getBytes(encoding);
        }
        catch (Throwable e) {
            throw new SysRuntimeException(e, "HttpUtil发送http请求出现异常"
                    + e.getMessage());
        }
        sendRequest(url, sdata);
    }

    public static String getRootPath(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/");
	}
    public static void main(String[] args) throws IOException {

    }

    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlName = url + "?" + param;
            URL realUrl = new URL(urlName);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 建立实际的连接
            conn.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定URL发送POST方法的请求
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式
     * @return URL所代表远程资源的响应
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

            conn.setDoOutput(true);// 发送POST请求必须设置如下两行
            conn.setDoInput(true);

            out = new PrintWriter(conn.getOutputStream());// 获取URLConnection对象对应的输出流s
            out.print(param);// 发送请求参数
            out.flush();// flush输出流的缓冲
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));// 定义BufferedReader输入流来读取URL的响应
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

}
