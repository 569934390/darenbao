package com.compses.util;

import com.compses.common.Constants;
import com.qiniu.util.Auth;

/**
 * Created by jocelynsuebb on 16/7/25.
 */
public class QiNiuUtil  {

    public static String getQiNiuUploadToken(){
        String accessKey = Constants.getContextProperty("QINIU_ACCESS_KEY","1").toString();
        String secretKey = Constants.getContextProperty("QINIU_SECRET_KEY","1").toString();
        String bucketName = Constants.getContextProperty("QINIU_BUCKET","1").toString();
        Auth auth = Auth.create(accessKey, secretKey);
        String token = auth.uploadToken(bucketName);
        return token;

    }
}
