package com.club.framework.util;

import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.web.common.cache.DBMetaCache;
import com.club.web.common.vo.DBColumn;
import com.club.web.common.vo.DBTable;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidformUtils {
    private static final ClubLogManager logger = ClubLogManager
            .getLogger(ValidformUtils.class);


    public static String defaultLength(String type){
        if("INT".equals(type)){
            return "11";
        }else if("VARCHAR".equals(type)){
            return "255";
        }else if("CHAR".equals(type)){
            return "255";
        }else if("TEXT".equals(type)){
            return "0";
        }else if("TINYINT".equals(type)){
            return "4";
        }else if("BIGINT".equals(type)){
            return "20";
        }else if("DOUBLE".equals(type)){
            return "0";
        }else if("BIT".equals(type)){
            return "1";
        }else if("DATETIME".equals(type)){
            return "0";
        }
        return "1" ;
    }



    public static void validTable(String tableName,Map<String,Object> record) throws RuntimeException {
        DBTable table = DBMetaCache.getTable(tableName);
        if (table==null){
            throw new RuntimeException("表【"+tableName+"】未找到！");
        }

        for(String fieldName : record.keySet()){
            if(table.getColumnMap().containsKey(fieldName)){
                DBColumn column = table.getColumnMap().get(fieldName);
                if(column.getType().indexOf("pk")!=-1)continue;//主键跳过
                if(StringUtils.isEmpty(record.get(fieldName))){
                    if(column.getIsNull().equals("0")) {
                        throw new RuntimeException("表“" + tableName + "”字段【" + fieldName + "】不允许为空");
                    }
                    else continue;
                }
                if(column.getDbType().toUpperCase().indexOf("CHAR")!=-1){
                    if(record.get(fieldName).toString().length()>=column.getLength()){
                        throw new RuntimeException("表“"+tableName+"”字段【"+fieldName+"】最长不能超过"+column.getLength());
                    }
                }
                if(column.getDbType().toUpperCase().equals("INT")){
                    record.put(fieldName, Integer.parseInt(record.get(fieldName)+""));
                }
                if(column.getDbType().toUpperCase().equals("LONG")){
                    record.put(fieldName, Long.parseLong(record.get(fieldName) + ""));
                }
                if(column.getDbType().toUpperCase().equals("Date")&&StringUtils.isEmpty(record.get(fieldName))){
                    record.put(fieldName,null);
                }
            }
            else{
                throw new RuntimeException("表“"+tableName+"”不存在字段【"+fieldName+"】！");
            }
        }
    }

    /**
     * 手机号验证
     *
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    /**
     * 电话号码验证
     *
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) {
        Pattern p1 = null,p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}?-?[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if(str.length() >9)
        {   m = p1.matcher(str);
            b = m.matches();
        }else{
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

}
