package com.club.web.util;

import javax.annotation.PostConstruct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.pili.Hub;
import com.pili.PiliException;
import com.pili.Stream;
import com.pili.Stream.StreamList;
import com.qiniu.Credentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.qiniu.util.Auth;
import scala.util.parsing.combinator.testing.Str;

import java.util.List;

/**
 * @author  Youzh
 * @describe 七牛云工具类
 * @version 1.0 
 * @date  2016年3月28日 上午11:08:19 
 * @return  
 */
@Component
@PropertySource("classpath:/config/qiniu.properties")
public class QiNiuUtils {
	
	@Value("${qiniu.access_key}") private String access_key;
	@Value("${qiniu.secret_key}") private String secret_key;
	@Value("${qiniu.bucketname}") private String bucketname;
    @Value("${qiniu.bucketnamefile}") private String bucketnamefile;
    @Value("${qiniu.hubname}") private String hubName;

	Auth qiNiuAuth;
    Credentials credentials;
    Hub hub;
	
	public String getQiNiuToken(){
		
		return qiNiuAuth.uploadToken(this.bucketname, null, 36000000, null);
	}
	public String getQiNiuFileToken(){
		
		return qiNiuAuth.uploadToken(this.bucketnamefile, null, 36000000, null);
	}

    public String createStream(String title){
        return createStream(title,null,null);
    }

    /**
     * 创建流
     * @param title
     * @param publishKey
     * @param publishSecurity
     * @return
     */
    public String createStream(String title,String publishKey,String publishSecurity){
        Stream stream = null;
        try {
            stream = hub.createStream(title, publishKey, publishSecurity);
            System.out.println("hub.createStream:");
            return stream.toJsonString();
            /*
            {
                "id":"z1.test-hub.55d97350eb6f92638c00000a",
                "createdAt":"2015-08-22T04:54:13.539Z",
                "updatedAt":"2015-08-22T04:54:13.539Z",
                "title":"55d97350eb6f92638c00000a",
                "hub":"test-hub",
                "disabled":false,
                "publishKey":"ca11e07f094c3a6e",
                "publishSecurity":"dynamic",
                "hosts":{
                    "publish":{
                        "rtmp":"ey636h.publish.z1.pili.qiniup.com"
                     },
                     "live":{
                         "hdl":"ey636h.live1-hdl.z1.pili.qiniucdn.com",
                         "hls":"ey636h.live1-hls.z1.pili.qiniucdn.com",
                         "rtmp":"ey636h.live1-rtmp.z1.pili.qiniucdn.com"
                     },
                     "playback":{
                         "hls":"ey636h.playback1.z1.pili.qiniucdn.com"
                     }
                 }
             }
             */
        } catch (PiliException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public String getStream(String streamId){
        Stream stream = null;
        // Get Stream
        try {
            stream = hub.getStream(streamId);
            System.out.println("hub.getStream:");
            return stream.toJsonString();
            /*
            {
                "id":"z1.test-hub.55d80075e3ba5723280000d2",
                "createdAt":"2015-08-22T04:54:13.539Z",
                "updatedAt":"2015-08-22T04:54:13.539Z",
                "title":"55d80075e3ba5723280000d2",
                "hub":"test-hub",
                "disabled":false,
                "publishKey":"ca11e07f094c3a6e",
                "publishSecurity":"dynamic",
                "hosts":{
                    "publish":{
                        "rtmp":"ey636h.publish.z1.pili.qiniup.com"
                     },
                     "live":{
                         "hdl":"ey636h.live1-hdl.z1.pili.qiniucdn.com",
                         "hls":"ey636h.live1-hls.z1.pili.qiniucdn.com",
                         "rtmp":"ey636h.live1-rtmp.z1.pili.qiniucdn.com"
                     },
                     "playback":{
                         "hls":"ey636h.playback1.z1.pili.qiniucdn.com"
                     }
                 }
             }
             */
        } catch (PiliException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public String listStreams(String status,String marker,long limit,String titlePrefix){
        // List streams
        try {
//            String status      = null;      // optional, can only be "connected"
//            String marker      = null;      // optional
//            long limit         = 0;         // optional
//            String titlePrefix = null;      // optional

            StreamList streamList = hub.listStreams(status, marker, limit, titlePrefix);
            System.out.println("hub.listStreams()");
            System.out.println("marker:" + streamList.getMarker());
            List<Stream> list = streamList.getStreams();
            JSONArray jsonArray=new JSONArray();
            for (Stream stream : list) {
                jsonArray.add(JSONObject.parseObject(stream.toJsonString()));
                // access the stream
            }
            return jsonArray.toJSONString();

            /*
             marker:10
             isEnd:false
             stream object
             */
        } catch (PiliException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //////////////////////////////////////////////////////////////////////////////////////////
        // Hub end
        //////////////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////////////////////////
        // Stream begin
        //////////////////////////////////////////////////////////////////////////////////////////
        return null;

        /*
            {
                "id":"z1.test-hub.55d80075e3ba5723280000d2",
                "createdAt":"2015-08-22T04:54:13.539Z",
                "updatedAt":"2015-08-22T04:54:13.539Z",
                "title":"55d80075e3ba5723280000d2",
                "hub":"test-hub",
                "disabled":false,
                "publishKey":"ca11e07f094c3a6e",
                "publishSecurity":"dynamic",
                "hosts":{
                    "publish":{
                        "rtmp":"ey636h.publish.z1.pili.qiniup.com"
                     },
                     "live":{
                         "hdl":"ey636h.live1-hdl.z1.pili.qiniucdn.com",
                         "hls":"ey636h.live1-hls.z1.pili.qiniucdn.com",
                         "rtmp":"ey636h.live1-rtmp.z1.pili.qiniucdn.com"
                     },
                     "playback":{
                         "hls":"ey636h.playback1.z1.pili.qiniucdn.com"
                     }
                 }
             }
         */
    }

    public String updateStream(String streamId,String newPublishKey,String newPublishSecurity,boolean newDisabled){
        // Update a Stream
//        String newPublishKey       = "new_secret_words"; // optional
//        String newPublishSecurity  = "static";           // optional, can be "dynamic" or "static"
//        boolean newDisabled        = true;               // optional, can be "true" of "false"
        try {
            Stream stream = hub.getStream(streamId);
            Stream newStream = stream.update(newPublishKey, newPublishSecurity, newDisabled);
            System.out.println("Stream update()");
            return newStream.toJsonString();
            /*
            {
                "id":"z1.test-hub.55d80075e3ba5723280000d2",
                "createdAt":"2015-08-22T04:54:13.539Z",
                "updatedAt":"2015-08-22T01:53:02.738973745-04:00",
                "title":"55d80075e3ba5723280000d2",
                "hub":"test-hub",
                "disabled":true,
                "publishKey":"new_secret_words",
                "publishSecurity":"static",
                "hosts":{
                    "publish":{
                        "rtmp":"ey636h.publish.z1.pili.qiniup.com"
                     },
                     "live":{
                         "hdl":"ey636h.live1-hdl.z1.pili.qiniucdn.com",
                         "hls":"ey636h.live1-hls.z1.pili.qiniucdn.com",
                         "rtmp":"ey636h.live1-rtmp.z1.pili.qiniucdn.com"
                     },
                     "playback":{
                         "hls":"ey636h.playback1.z1.pili.qiniucdn.com"
                     }
                 }
             }
         */
        } catch (PiliException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public boolean disableStream(String streamId){
        // Disable a Stream
        try {
            Stream stream=hub.getStream(streamId);
            Stream disabledStream = stream.disable();
            System.out.println("Stream disable()");
            return disabledStream.isDisabled();
            /*
             * true
             *
             * */
        } catch (PiliException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    public boolean enableStream(String streamId){
        // Enable a Stream
        try {
            Stream stream=hub.getStream(streamId);
            Stream enabledStream = stream.enable();
            System.out.println("Stream enable()");
            return !enabledStream.isDisabled();
            /*
             * false
             *
             * */
        } catch (PiliException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    public String getStreamStatus(String streamId){
        // Get Stream status
        try {
            Stream stream=hub.getStream(streamId);
            Stream.Status status = stream.status();
            System.out.println("Stream status()");
            return status.toString();
            /*
            {
                "addr":"222.73.202.226:2572",
                "startFrom": "2015-09-10T05:58:10.289+08:00",
                "status":"disconnected",
                "bytesPerSecond":0,
                "framesPerSecond":{
                    "audio":0,
                    "video":0,
                    "data":0
                 }
             }
            */
        } catch (PiliException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public String createRTMPPublishUrl(String streamId){
        // Generate RTMP publish URL
        try {
            Stream stream=hub.getStream(streamId);
            String publishUrl = stream.rtmpPublishUrl();
            System.out.println("Stream rtmpPublishUrl()");
           return publishUrl;
            // rtmp://ey636h.publish.z1.pili.qiniup.com/test-hub/55d810aae3ba5723280000db?nonce=1440223404&token=hIVJje0ZOX9hp7yPIvGBmJ_6Qxo=

        } catch (PiliException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public String createRTMPLiveUrl(String streamId){
        // Generate RTMP live play URLs
        Stream stream= null;
        try {
            stream = hub.getStream(streamId);
            String originUrl = stream.rtmpLiveUrls().get(Stream.ORIGIN);
            System.out.println("Stream rtmpLiveUrls()");
            return originUrl;
            // rtmp://ey636h.live1-rtmp.z1.pili.qiniucdn.com/test-hub/55d8113ee3ba5723280000dc
        } catch (PiliException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String createHLSLiveUrl(String streamId){
        // Generate HLS play URLs
        Stream stream= null;
        try {
            stream = hub.getStream(streamId);
            String originUrl = stream.hlsLiveUrls().get(Stream.ORIGIN);
            System.out.println("Stream hlsLiveUrls()");
            return originUrl;
            // http://ey636h.live1-http.z1.pili.qiniucdn.com/test-hub/55d8119ee3ba5723280000dd.m3u8
        } catch (PiliException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String createHLSPlaybackUrl(String streamId,long startHlsPlayback,long endHlsPlayback){
        // Generate HLS playback URLs
//        long startHlsPlayback     = 1440315411;  // required, in second, unix timestamp
//        long endHlsPlayback       = 1440315435;  // required, in second, unix timestamp
        try {
            Stream stream=hub.getStream(streamId);
            String hlsPlaybackUrl = stream.hlsPlaybackUrls(startHlsPlayback, endHlsPlayback).get(Stream.ORIGIN);

            System.out.println("Stream hlsPlaybackUrls()");
            return hlsPlaybackUrl;
            // http://ey636h.playback1.z1.pili.qiniucdn.com/test-hub/55d8119ee3ba5723280000dd.m3u8?start=1440315411&end=1440315435
        } catch (PiliException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public String snapShotStream(String streamId,String format,String name,long time,String notifyUrl){
        // Snapshot Stream
//        String format    = "jpg";                      // required
//        String name      = "imageName" + "." + format; // required
//        long time        = 1440315411;                 // optional, in second, unix timestamp
//        String notifyUrl = null;                       // optional

        try {
            Stream stream=hub.getStream(streamId);
            Stream.SnapshotResponse response = stream.snapshot(name, format, time, notifyUrl);
            System.out.println("Stream snapshot()");
            return response.toString();
            /*
             {
                 "targetUrl":"http://ey636h.static1.z0.pili.qiniucdn.com/snapshots/z1.test-hub.55d81a72e3ba5723280000ec/imageName.jpg",
                 "persistentId":"z1.55d81c247823de5a49ad729c"
             }
             */
        } catch (PiliException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public String saveStreamAsFile(String streamId,String saveAsFormat,String saveAsName,long saveAsStart,long saveAsEnd,String saveAsNotifyUrl){
        // Save Stream as a file
//        String saveAsFormat    = "mp4";                            // required
//        String saveAsName      = "videoName" + "." + saveAsFormat; // required
//        long saveAsStart       = 1440315411;                       // required, in second, unix timestamp
//        long saveAsEnd         = 1440315435;                       // required, in second, unix timestamp
//        String saveAsNotifyUrl = null;                             // optional
        try {
            Stream stream=hub.getStream(streamId);
            Stream.SaveAsResponse response = stream.saveAs(saveAsName, saveAsFormat, saveAsStart, saveAsEnd, saveAsNotifyUrl);
            System.out.println("Stream saveAs()");
            return response.toString();
            /*
             {
                 "url":"http://ey636h.vod1.z1.pili.qiniucdn.com/recordings/z1.test-hub.55d81a72e3ba5723280000ec/videoName.m3u8",
                 "targetUrl":"http://ey636h.vod1.z1.pili.qiniucdn.com/recordings/z1.test-hub.55d81a72e3ba5723280000ec/videoName.mp4",
                 "persistentId":"z1.55d81c6c7823de5a49ad77b3"
             }
            */
        } catch (PiliException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public String deleteStream(String streamId){
        // Delete a Stream
        try {
            Stream stream=hub.getStream(streamId);
            String res = stream.delete();
            System.out.println("Stream delete()");
            return res;
            // No Content
        } catch (PiliException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }
    public String createFLVLiveUrl(String streamId){
        //  Generate Http-Flv live play URLs
        Stream stream= null;
        try {
            stream = hub.getStream(streamId);
            String originUrl = stream.httpFlvLiveUrls().get(Stream.ORIGIN);
            System.out.println("Stream httpFlvLiveUrls()");
            return originUrl;
            // http://ey636h.live1-http.z1.pili.qiniucdn.com/test-hub/55d8119ee3ba5723280000dd.flv
        } catch (PiliException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Stream.SegmentList getStreamSegments(String streamId,long start,long end,int limit){
        // Get Stream segments
//        long start = 0;    // optional, in second, unix timestamp
//        long end   = 0;    // optional, in second, unix timestamp
//        int limit  = 0;    // optional, int
        try {
            Stream stream=hub.getStream(streamId);
            Stream.SegmentList segmentList = stream.segments(start, end, limit);

//            System.out.println("The earliest data of stream:" + segmentList.getStart()
//                    + ",the latest data of stream:" + segmentList.getEnd());
//
//            System.out.println("The duration of the current segment:" + segmentList.getDuration());

            System.out.println("Stream segments()");
            for (Stream.Segment segment : segmentList.getSegmentList()) {
                System.out.println("start:" + segment.getStart() + ",end:" + segment.getEnd());
            }
            return segmentList;
            /*
                 The earliest data of stream:1444298545,the latest data of stream:1444298612
                 The duration of the current segment:67
                 start:1440315411,end:1440315435
             */
        } catch (PiliException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
	@PostConstruct
	public void init(){
        qiNiuAuth= Auth.create(access_key, secret_key);
        credentials=new Credentials(this.access_key,this.secret_key);
        hub=new Hub(credentials,this.hubName);
	}
}
