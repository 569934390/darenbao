package com.compses.util;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jocelynsuebb on 16/9/6.
 */
public class PaBankUtil {

    /**
     * 橙子银行
     * @param recvMessageString
     * @return
     */
    public static String getRecvMessageString(String recvMessageString){
        String ss = recvMessageString.substring(recvMessageString.indexOf("<ROOT>"),recvMessageString.indexOf("</ROOT>")+7);
        return ss;
    }

    /**
     * 银企直联
     * @param recvMessageString
     * @return
     */
    public static String getDirectRecvMessageString(String recvMessageString){
        String ss = recvMessageString.substring(recvMessageString.indexOf("<Result>"),recvMessageString.indexOf("</Result>")+9);
        return ss;
    }

    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     * @param strxml
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    public static Map doXMLParse(String strxml,Map m) throws JDOMException, IOException {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

        if(null == strxml || "".equals(strxml)) {
            return null;
        }

//        Map m = new HashMap();

        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while(it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if(children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = XMLUtil.getChildrenText(children);
            }

            m.put(k, v);
        }

        //关闭流
        in.close();

        return m;
    }

    /**
     * 获取子结点的xml
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if(!children.isEmpty()) {
            Iterator it = children.iterator();
            while(it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if(!list.isEmpty()) {
                    sb.append(XMLUtil.getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    /**
     * 获取xml编码字符集
     * @param strxml
     * @return
     * @throws IOException
     * @throws JDOMException
     */
    public static String getXMLEncoding(String strxml) throws JDOMException, IOException {
        InputStream in = HttpClientUtil.String2Inputstream(strxml);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        in.close();
        return (String)doc.getProperty("encoding");
    }

    /**
     * 获取流水
     * @return
     */
    public static String getThirdLogNo(){
        //流水号
        //交易日期
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String rdNum=df.format(new Date());
        Random random = new Random();
        int ird = random.nextInt(999999);
        String srd= String.format("%06d", ird);
        String thirdLogNo = rdNum + srd;
        return thirdLogNo;
    }

    public static void main(String[] args) {
//        String ss = "<ROOT><state>00</state><returnUrl></returnUrl><cardNo>6222980110352665</cardNo><isNew>0</isNew><errorCode></errorCode><errorMessage></errorMessage></ROOT>";
//
//        try {
//            Map map = doXMLParse(ss);
//            System.out.print("SDf");
//        } catch (JDOMException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
