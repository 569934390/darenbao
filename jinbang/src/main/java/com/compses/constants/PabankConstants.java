package com.compses.constants;

/**
 * Created by jocelynsuebb on 16/9/5.
 *
 * 平安银行-橙子银行交易类型常量
 */
public class PabankConstants {

    /**
     * 通讯系统-橙子银行
     */
    public static final String TRAN_SYS_CHENGZI="22";

    /**
     * 通讯系统-银企直联
     */
    public static final String TRAN_SYS_BANK_DIRECT="01";

    /**
     * 橙子银行-开户
     */
    public static final String TRADE_KAIHU = "000001";

    /**
     * 橙子银行-激活
     */
    public static final String TRADE_JIHUO = "000002";

    /**
     * 银企直联-转账
     */
    public static final String TRADE_ZHUANZHANG="4004";

    /**
     * 银企直联-转账查询结果
     */
    public static final String TRADE_ZHUANZHANG_CHAXUN="4005";

    /**
     * 报文发送IP
     */
    public static final String SERVER_IP_ADDRESS = "115.159.65.207";

    /**
     * 报文发送端口
     */
    public static final int SERVER_PORT = 7072;

    /**
     * 外联客户代码：银行业务为商家分配的商户号 测00102079900001231000 --橙子
     * 近帮:20160901201609010001
     * 测试银企直连的客户代码--01001080000007501000
     */
    public static final String KHDM = "01001080000007501000";

    /**
     * 报文返回的成功失败的标志--成功
     */
    public static final String PABANK_ERROR_CODE_SUCCESS="00";

    /**
     * 报文返回的成功失败的标志--失败
     */
    public static final String PABANK_ERROR_CODE_FAILE="99";

    /**
     * 激活成功,回调通知激活成功
     */
    public static final String PABANK_CALLBACK_URL = "http://115.159.65.207:38080/jinbang";


    public static final String PABANK_CCY_CODE="RMB";
}
