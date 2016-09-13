package com.compses.util;

import com.compses.common.Constants;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;


/**
 * Created by nini on 2016/4/2.
 */

public class SystemUtil {



    public static String  uploadMultipart(Map<String, MultipartFile> fileMap,String path)throws Exception{
        String iconPath = Constants.getContextProperty("photoType").toString();
        String newpath =iconPath+path;
        //获取文件集合
        File f = new File(newpath);
        if(!f.exists()){ f.mkdirs();}
        String result = "";
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            //获取多媒体文件信息
            MultipartFile mf = entity.getValue();
            //获取字节数组
            byte[] bytes = mf.getBytes();
            //获取上传的文件的名字
            String fname = StringUtils.getUUID()+mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf("."));
            FileUtils.save(newpath + fname, bytes);
            result =result+path+fname+",";
         }
        result =result.substring(0,result.length()-1);
        return result;
    }

    /**
     * 生成订单号
     * @return
     */
    public static String makeOrderCode(int max){
        Date now = new Date();
        Random random = new Random();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//可以方便地修改日期格式
        String orderCode = dateFormat.format(now);
        try {
//            orderCode = "JB"+(Md5PasswordEncoder.md5Encode(orderCode).substring(8,24));
            orderCode="JB"+orderCode+random.nextInt(max);
        }catch (Exception e){

        }
        return orderCode;
    }

    public static void main(String[] args) {
        System.out.println(makeOrderCode(1000));
    }

    /**
     * 生成退款单号
     * @return
     */
    public static String makeRefundCode(){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
        String orderCode = dateFormat.format(now);
        try {
            orderCode = "xzbRefund"+(Md5PasswordEncoder.md5Encode(orderCode).substring(8,24));
        }catch (Exception e){

        }
        return orderCode;
    }

    public static String makeCompanyCode(){
        //取四位字符
        String base = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        //取四位数字
        String num ="0123456789";
        for (int i = 0; i < 4; i++) {
            int number = random.nextInt(num.length());
            sb.append(num.charAt(number));
        }
        return sb.toString();
    }




}
