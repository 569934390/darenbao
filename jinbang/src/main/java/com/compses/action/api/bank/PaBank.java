package com.compses.action.api.bank;

import com.compses.constants.PabankConstants;
import com.compses.util.PaBankUtil;
import com.compses.util.PayUtil;
import com.compses.util.XMLUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jocelynsuebb on 16/9/4.
 */
public class PaBank {

//    private Logger logger= LoggerFactory.getLogger(this.getClass());

    public final static String chartSet = "GBK";

    public static void main(String[] args) {
        String res = null;
//        PostMethod postMethod = new PostMethod("http://115.159.65.207:7072");
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
//        parameters.put("userName","徐彬彬");
//        parameters.put("cardNumber","35021219880423253X");
//        parameters.put("mobile","13859924100");
//        parameters.put("channelType","0");
//        parameters.put("umCode","");
        parameters.put("cardNo","6222980110352665");
        parameters.put("callBackUrl","http://115.159.65.207:38080/jinbang");

        String requestXML = getRequestXml(parameters);
        try {
            Map retKeyDict = new HashMap();
            requestTCP(requestXML, PabankConstants.KHDM,PabankConstants.TRADE_JIHUO,retKeyDict,PabankConstants.TRAN_SYS_CHENGZI);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void requestTCP(String tranMessage,String khdm , String tranFunc,Map retKeyDict,String tranSys) throws Exception{
        String tranHead = createTranHead(tranMessage, khdm , tranFunc,tranSys);
        System.out.println("报文头长度:"+tranHead.length());
        sendTranMessage(tranHead+tranMessage,PabankConstants.SERVER_IP_ADDRESS,PabankConstants.SERVER_PORT,retKeyDict,tranSys);
        //问题3 配置的端口是300003 为什么这里用7072
        //问题4
    }

    /**
     * @param tranMessageBody 报文体
     * @param khdm  客户代码
     * @param tranFunc  交易代码
     * @param tranSys  交易系统代码
     * @return
     */
    public static String createTranHead(String tranMessageBody,String khdm,String tranFunc,String tranSys) throws Exception {

        String tranHead = "";

        //A001:报文版本  22:目标系统ID  01:报文编码GBK  01:通讯协议TCP
        String basic = "A001"+tranSys+"0101";

        //客户代码:测试环境客户代码:00102079900001231000
//        String khdm = "00102079900001231000";

        //XML报文体数据的字节长度
        String hLength = null;
        byte[] byteMessageBody = null;
        byteMessageBody = tranMessageBody.getBytes("UTF-8");//编码
        hLength = String.format("%010d", byteMessageBody.length);
        //问题1:下面是见证宝获取XML报文体字节长度,橙子银行是否需要组装这种报文头
        /*组公网业务报文头*/
//        int iLength=byteMessageBody.length;
//        String hLength=String.format("%08d", iLength);
        /*组公网通讯报文头*/
//        int iNetLength=iLength+122;
//        String hNetLength=String.format("%010d", iNetLength);

        //橙子银行交易码:000001
//        String tranFunc="000001";

        //操做员代码+服务类型非必输 7个空格
        //问题2:文档中这种空格是不是这么来的?
        String headPart1 = "";
        if(tranSys.equals(PabankConstants.TRAN_SYS_CHENGZI)){
            headPart1="       ";
        }else{
            headPart1="         ";
        }


        //交易日期
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String hTrandateTime=df.format(new Date());

        //流水号
        String rdNum=df.format(new Date());
        Random random = new Random();
        int ird = random.nextInt(999999);
        String srd= String.format("%06d", ird);
        String thirdLogNo = rdNum + srd;

        //返回码:请求非必输，请求时必须填写000000
        String headPart2="000000";

        //返回描述:非必输 补全空格
        String headPart3="                                                                                                    ";

        //后续包标志+请求次数+签名标识
        String headPart4="00000";

        //签名数据包格式+签名算法
        String headPart5="             ";

        String headPart6="00000000000";

        tranHead = basic + khdm + hLength + tranFunc + headPart1 + hTrandateTime + thirdLogNo + headPart2 + headPart3 + headPart4 + headPart5 + headPart6;

        return tranHead;
    }


    public static String getRequestXml(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<Root>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("callBackUrl".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</Root>");
//        sb.append("</xml>");
        return sb.toString();
    }

    public static String getDirectRequestXml(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<Result>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append("<" + k + ">" + v + "</" + k + ">");
        }
        sb.append("</Result>");
//        sb.append("</xml>");
        return sb.toString();
    }


    /*发送报文，并接收银行返回*/
    public static void sendTranMessage(String tranMessage,String serverIPAddress, int serverPort,Map retKeyDict,String tranSys) throws Exception{


            Socket s = new Socket(serverIPAddress,serverPort);
            s.setSendBufferSize(4096);
            s.setTcpNoDelay(true);
            s.setSoTimeout(60000);
            s.setKeepAlive(true);
            //Socket s = new Socket(InetAddress.getByName(null),port);
            OutputStream os=s.getOutputStream();
            InputStream is=s.getInputStream();

            os.write(tranMessage.getBytes("UTF-8"));
            os.flush();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte []buf=new byte[4096];
            int len = -1;
            while ((len = is.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
            byte[] byteContent = bos.toByteArray();
            String recvMessage = new String(byteContent, "GBK");
            System.out.println("银行返回报文:"+recvMessage);
//            recvMessage = recvMessage + '\0';//修复使用spilt方法时的数组越界错误
//            retKeyDict.put("RecvMessage", recvMessage);
            //<cardNo>6222980110352665</cardNo>
            if(tranSys.equals(PabankConstants.TRAN_SYS_BANK_DIRECT) && recvMessage.contains("交易受理成功")){
                recvMessage = PaBankUtil.getDirectRecvMessageString(recvMessage);
            }
            else if (tranSys.equals(PabankConstants.TRAN_SYS_CHENGZI)){
                recvMessage = PaBankUtil.getRecvMessageString(recvMessage);
            }

            retKeyDict = PaBankUtil.doXMLParse(recvMessage,retKeyDict);
            os.close();
            is.close();




    }


    /**
     * 查询是否交易成功
     * @param thirdVoucher
     */
    public static Map chaXunZhuanZhang(String thirdVoucher,Map retKeyDict){
//        A0010101010100108000000750100000000006024004       022016090914050920160909140509472830000000:交易受理成功                                                                                       00000             00000000000<?xml version="1.0" encoding="GBK" ?><Result><ThirdVoucher>20160909140508550077</ThirdVoucher><FrontLogNo>51609096369265</FrontLogNo><CcyCode>RMB</CcyCode><OutAcctName>平安测试保险</OutAcctName><OutAcctNo>11000593657501</OutAcctNo><InAcctBankName>平安银行</InAcctBankName><InAcctNo>6216260000200001767</InAcctNo><InAcctName>葛盛涛</InAcctName><TranAmount>0.01</TranAmount><UnionFlag>1</UnionFlag><Fee1>0.00</Fee1><Fee2>0.00</Fee2><SOA_VOUCHER></SOA_VOUCHER><hostFlowNo>0141767</hostFlowNo><hostTxDate>20160916</hostTxDate><Mobile></Mobile><feeTax>0.00</feeTax><CstInnerFlowNo></CstInnerFlowNo></Result>
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("OrigThirdVoucher",thirdVoucher);
        parameters.put("OrigFrontLogNo","");
        String tranMessageBodyXml = PaBank.getDirectRequestXml(parameters);
        try {
            PaBank.requestTCP(tranMessageBodyXml, PabankConstants.KHDM, PabankConstants.TRADE_ZHUANZHANG_CHAXUN,retKeyDict,PabankConstants.TRAN_SYS_BANK_DIRECT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retKeyDict;
    }
}
