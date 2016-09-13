package com.compses.util;

import com.compses.constants.PayConstants;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * Created by jocelynsuebb on 2016/3/11.
 */
public class PayUtil {

    /**
     * @author 老妖
     * @date 2014-12-5下午2:32:05
     * @Description：将请求参数转换为xml格式的string
     * @param parameters
     *            请求参数
     * @return
     */
    public static String getRequestXml(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)|| "sign".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * @author lwz
     * @date 2014-12-8
     * @Description：sign签名
     * @param characterEncoding
     *            编码格式
     * @param parameters
     *            请求参数
     * @return
     */
    public static String createSign(String characterEncoding,SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + PayConstants.WEBCHAT_APP_KEY);
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    /**
     * 创建签名SHA1
     *
     * @return
     * @throws Exception
     */
    public String createSHA1Sign(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + v + "&");
        }
        String params = sb.substring(0, sb.lastIndexOf("&"));
        String appsign = Sha1Util.getSha1(params);
//        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "sha1 sb:" + params);
//        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "app sign:" + appsign);
        return appsign;
    }
}
