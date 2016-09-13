package com.compses.util;


import com.compses.common.Constants;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

public class QiniuUtils {


	public static String QINIU_ACCESS_KEY = Constants.getContextProperty("QINIU_ACCESS_KEY").toString();

	public static String QINIU_SECRET_KEY = Constants.getContextProperty("QINIU_SECRET_KEY").toString();

	public static String QINIU_BUCKET = Constants.getContextProperty("QINIU_BUCKET").toString();
	
	Auth auth = Auth.create(QINIU_ACCESS_KEY, QINIU_SECRET_KEY);
	
	
	private String getUpToken0(){
	    return auth.uploadToken(QINIU_BUCKET);
	}

	private String getUpToken1(){
	    return auth.uploadToken(QINIU_BUCKET, "key");
	}

	private String getUpToken2(){
	    return auth.uploadToken(QINIU_BUCKET, null, 3600, new StringMap()
	         .put("callbackUrl", "call back url").putNotEmpty("callbackHost", "")
	         .put("callbackBody", "key=$(key)&hash=$(etag)"));
	}

	private String getUpToken3(){
	    return auth.uploadToken(QINIU_BUCKET, null, 3600, new StringMap()
	            .putNotEmpty("persistentOps", "").putNotEmpty("persistentNotifyUrl", "")
	            .putNotEmpty("persistentPipeline", ""), true);
	}
	

	

	
	public void uploadFile(byte[] bytes,String newFileName ){
		
//		File file = new File("F:/first.jpg");
		UploadManager uploadManager = new UploadManager();
	    try {
	        Response res = uploadManager.put(bytes, newFileName, getUpToken0());
	        // log.info(res);
	        // log.info(res.bodyString());
	        // Ret ret = res.jsonToObject(Ret.class);
	    } catch (QiniuException e) {
	        Response r = e.response;
	        System.out.println(r.toString());
//	        log.error(r.toString());
	        try {
	        	System.out.println(r.bodyString());
//	            log.error(r.bodyString());
	        } catch (QiniuException e1) {
	            //ignore
	        }
	    }
	}
	
	public static void main(String[] args) {
		QiniuUtils u = new QiniuUtils();
//		u.uploadFile();
	}
}
