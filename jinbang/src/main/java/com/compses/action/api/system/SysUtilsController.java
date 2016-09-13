package com.compses.action.api.system;

import com.compses.common.Constants;
import com.compses.constants.SystemConstants;
import com.compses.framework.ResultData;
import com.compses.redis.util.RedisHashSetUtil;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by nini on 2016/6/6.
 */

@Controller
@RequestMapping("sysUtilsController")
public class SysUtilsController {

    /**
     * 获取图片
     *
     * @param request
     * @param response
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/getFile.do")
    @ResponseBody
    public void getfile(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String iconPath = Constants.getContextProperty("photoType").toString();
//                CustomPropertyConfigurer.getContextProperty("uploadPath").toString() + "icons/";

        if (request.getParameter("getfile") != null
                && !request.getParameter("getfile").isEmpty()) {
            File file = new File(iconPath,
                    request.getParameter("getfile"));
            if (file.exists()) {
                int bytes = 0;
                ServletOutputStream op = response.getOutputStream();

                response.setContentType(getMimeType(file));
                response.setContentLength((int) file.length());
                response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

                byte[] bbuf = new byte[1024];
                DataInputStream in = new DataInputStream(new FileInputStream(file));

                while ((in != null) && ((bytes = in.read(bbuf)) != -1)) {
                    op.write(bbuf, 0, bytes);
                }

                in.close();
                op.flush();
                op.close();
            }
        } else if (request.getParameter("delfile") != null && !request.getParameter("delfile").isEmpty()) {
            File file = new File(iconPath, request.getParameter("delfile"));
            if (file.exists()) {
                file.delete(); // TODO:check and report success
            }
        } else if (request.getParameter("getthumb") != null && !request.getParameter("getthumb").isEmpty()) {
            File file = new File(iconPath, request.getParameter("getthumb"));
            if (file.exists()) {
                String mimetype = getMimeType(file);
                String fileName = file.getName();
                if (fileName.endsWith("png") || fileName.endsWith("jpeg") || fileName.endsWith("gif") ||fileName.endsWith("jpg")) {
                    BufferedImage im = ImageIO.read(file);
                    if (im != null) {
                        int size = 200;
                        int width = 0;
                        int height = 0;
                        if (request.getParameter("size") != null) {
                            size = Integer.parseInt(request.getParameter("size"));
                        }
                        if (request.getParameter("width") != null) {
                            width = Integer.parseInt(request.getParameter("width"));
                        }
                        if (request.getParameter("height") != null) {
                            height = Integer.parseInt(request.getParameter("height"));
                        }
                        BufferedImage thumb;
                        if (width != 0 && height != 0) {
                            thumb = Scalr.resize(im, width, height);
                        } else if (width != 0) {
                            thumb = Scalr.resize(im, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_WIDTH, width);
                        } else if (height != 0) {
                            thumb = Scalr.resize(im, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, height);
                        } else {
                            thumb = Scalr.resize(im, size);
                        }

                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        ImageIO.write(thumb, "PNG", os);
                        response.setContentType("image/png");
                        ServletOutputStream srvos = response.getOutputStream();
                        response.setContentLength(os.size());
                        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
                        os.writeTo(srvos);
                        srvos.flush();
                        srvos.close();
                    }
                }
            } // TODO: check and report success
            else {
                response.sendRedirect(request.getContextPath()+"/image/user5-128x128.png");
            }
        } else {
            PrintWriter writer = response.getWriter();
            writer.write("call POST with multipart form data");
        }
    }

    public String getMimeType(File file) {
        String mimetype = "";
        if (file.exists()) {
            if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
                mimetype = "image/png";
            } else {
                javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
                mimetype = mtMap.getContentType(file);
            }
        }
        return mimetype;
    }

    private String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        return suffix;
    }

    @RequestMapping(value = "/getApp.do")
    @ResponseBody
    public ResultData getApp(String device){
        ResultData resultData = new ResultData();
        try{
            if (device.equals(SystemConstants.DEVICE_IOS)){
                resultData.setReturnMsg(true,"http://fir.im/jinbangi");
            }else if(device.equals(SystemConstants.DEVICE_ANDROID)){
                resultData.setReturnMsg(true,"http://fir.im/jinbanga");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }
}
