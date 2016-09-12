package com.compses.action.api.bank;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jocelynsuebb on 16/9/10.
 */
public class PaBankChaXun {

    public static void main(String[] args) {
        Map retKeyDict = new HashMap();
        PaBank.chaXunZhuanZhang("20160909140508550077",retKeyDict);
        System.out.print("d");
    }
}
