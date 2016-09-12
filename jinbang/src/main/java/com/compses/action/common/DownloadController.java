package com.compses.action.common;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.compses.common.Constants;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

/**
 * 文件下载控制器，目前提供三种下载方式
 * 1、提供文件(文件上传)路径(filePath：即文件路径+文件名)和文件名(fileName)，这种方式适用于文件路径和文件名分开存放的情况
 * 2、提供相对(项目)路径(fullPath：即文件路径+文件名)和文件名(fileName)，这种方式适用于文件重命名的情况，一般是服务器将真实文件名重命名为字母加数字的形式，然后在数据库中保存真实文件名，以避免文件乱码
 * 3、提供多个文件(文件上传)路径(filePath，用","分隔)和多个文件名(fileName，用","分隔)，且multiFile不为空，这种情况为多文件下载
 * @author hu.bo<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014年12月11日 <br>
 * @since V1.0<br>
 */
@Controller
public class DownloadController {

   /* private static final ClubLogManager logger = ClubLogManager.getLogger(DownloadController.class);

    @RequestMapping("download")
    public void sendRedirect(HttpServletRequest request,
                             HttpServletResponse response) throws IOException{
        File resultFile = null ;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("filePath");
        String fullPath = request.getParameter("fullPath");
        String multiFile = request.getParameter("multiFile");
        boolean needZip = false;  //如果是多文件下载，则需要打包压缩
        String basePath = "";
        if(StringUtils.isNotEmpty(multiFile)){
            needZip = true;
        }
        if(StringUtils.isNotEmpty(fullPath)){
            basePath = request.getSession().getServletContext().getRealPath("/");
            filePath = fullPath;
        } else{
            basePath = Configuration.getString("uploadPath");
        }
        if(StringUtils.isEmpty(filePath)){
            response.sendError(405, "filePath is null!");
            logger.error("下载失败！filePath and fileName is null");
            return;
        }
        if(StringUtils.isEmpty(fileName)){
            response.sendError(405, "fileName is null!");
            logger.error("下载失败！fileName is null");
            return;
        }else{
            fileName = new String(fileName.getBytes("iso-8859-1"),"utf-8");
        }
        resultFile = getFile(basePath, filePath, fileName, needZip);
        if(needZip){
            fileName = resultFile.getName();
        }
        logger.info("start download fileName {0}  ,  filePath  {1}",fileName,filePath);
        FileInputStream resultFileFi = new FileInputStream(resultFile);
        long l = resultFile.length();
        int k = 0;
        byte abyte0[] = new byte[65000];
        response.setContentType("application/x-msdownload");
        response.setContentLength((int) l);
        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1") );
        while ((long) k < l) {
            int j;
            j = resultFileFi.read(abyte0, 0, 65000);
            k += j;
            response.getOutputStream().write(abyte0, 0, j);
        }
        resultFileFi.close();
        if(needZip){
            resultFile.delete();
        }
    }

    private File getFile(String basePath,String filePath,String fileName,boolean needZip){
        File resultFile = null;
        if(needZip){
            List<String> files = Arrays.asList(filePath.split(","));
            List<String> realNames = Arrays.asList(fileName.split(","));
            String zipFileName = realNames.get(0);
            zipFileName = zipFileName.substring(0, zipFileName.lastIndexOf(".")) + "等.zip";
            ZipCompressor zc = new ZipCompressor(basePath + zipFileName);
            zc.compress(files, realNames,basePath);
            resultFile = zc.getZipFile();
        }else{
            resultFile = new File(basePath + filePath);
        }
        return resultFile;
    }

    @RequestMapping(value = "/upload",method = RequestMethod.GET)
    public void getfile(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String iconPath = CustomPropertyConfigurer.getContextProperty("uploadPath").toString() + "icons/";

        if (request.getParameter("getfile") != null
                && !request.getParameter("getfile").isEmpty()) {
            File file = new File(iconPath,
                    request.getParameter("getfile"));
            if (file.exists()) {
                int bytes = 0;
                ServletOutputStream op = response.getOutputStream();

                response.setContentType(getMimeType(file));
                response.setContentLength((int) file.length());
                response.setHeader( "Content-Disposition", "inline; filename=\"" + file.getName() + "\"" );

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
                if (mimetype.endsWith("png") || mimetype.endsWith("jpeg") || mimetype.endsWith("gif")) {
                    BufferedImage im = ImageIO.read(file);
                    if (im != null) {
                        int size = 200;
                        int width = 0;
                        int height = 0;
                        if (request.getParameter("size")!=null){
                            size = Integer.parseInt(request.getParameter("size"));
                        }
                        if (request.getParameter("width")!=null){
                            width = Integer.parseInt(request.getParameter("width"));
                        }
                        if (request.getParameter("height")!=null){
                            height = Integer.parseInt(request.getParameter("height"));
                        }
                        BufferedImage thumb;
                        if(width!=0 && height!=0){
                            thumb = Scalr.resize(im, width, height);
                        }else if(width!=0){
                            thumb = Scalr.resize(im, Method.AUTOMATIC, Mode.FIT_TO_WIDTH, width);
                        }else if(height!=0){
                            thumb = Scalr.resize(im, Method.AUTOMATIC, Mode.FIT_TO_HEIGHT, height);
                        }else{
                            thumb = Scalr.resize(im, size);
                        }
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        if (mimetype.endsWith("png")) {
                            ImageIO.write(thumb, "PNG" , os);
                            response.setContentType("image/png");
                        } else if (mimetype.endsWith("jpeg")) {
                            ImageIO.write(thumb, "jpg" , os);
                            response.setContentType("image/jpeg");
                        } else {
                            ImageIO.write(thumb, "GIF" , os);
                            response.setContentType("image/gif");
                        }
                        ServletOutputStream srvos = response.getOutputStream();
                        response.setContentLength(os.size());
                        response.setHeader( "Content-Disposition", "inline; filename=\"" + file.getName() + "\"" );
                        os.writeTo(srvos);
                        srvos.flush();
                        srvos.close();
                    }
                }
            } // TODO: check and report success
            else{
                response.sendRedirect("image/user5-128x128.jpg");
            }
        } else {
            PrintWriter writer = response.getWriter();
            writer.write("call POST with multipart form data");
        }
    }*/

    @RequestMapping(value="/upload",method = RequestMethod.POST)
    @ResponseBody
    public Map fileUpload(DefaultMultipartHttpServletRequest request,HttpServletResponse response) throws IOException {

        Map<String,Object> result = new HashMap<String, Object>();
        try {
//            String iconPath = CustomPropertyConfigurer.getContextProperty("uploadPath").toString() + "icons/";
            String iconPath = Constants.getContextProperty("uploadPath").toString();
            File f = new File(iconPath);
            if(!f.exists()){ f.mkdirs();}
            long iconNameTime = System.currentTimeMillis();
            for (Iterator<String> iter = request.getFileNames();iter.hasNext();){
                String fileName = iter.next();
                String iconName = request.getFile(fileName).getOriginalFilename();
                String iconNames =  iconName.substring(iconName.lastIndexOf("."));
                iconName = iconNameTime + iconNames;
                //为避免中文乱码问题，将文件名统一用时间戳命名
                String path=iconPath + iconName;
                FileOutputStream os = new FileOutputStream(path);
                InputStream in = request.getFile(fileName).getInputStream();
                int b = 0;
                while ((b = in.read()) != -1) {
                    os.write(b);
                }
                os.flush();
                in.close();
                os.close();
                String hashFileName = URLEncoder.encode(iconName, "UTF-8");

                result.put("iconname", hashFileName);
                result.put("iconpath",path);
                result.put("size", request.getFile(fileName).getSize());
                result.put("url", "upload.do?getfile=" + hashFileName);
                result.put("thumbnail_url", "upload.do?getthumb=" + hashFileName);
                result.put("delete_url", "upload.do?delfile=" + hashFileName);
                result.put("delete_type", "GET");
                result.put("success", true);
//                rs.add(result);
/*                String json  = "{success:true,iconname:'" + iconName + "',iconpath:'icons/'}";
                response.setContentType("text/html");
                response.getWriter().write(json);
                response.flushBuffer();*/
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {

        }
        return result;
    }

    public String getMimeType(File file) {
        String mimetype = "";
        if (file.exists()) {
//            URLConnection uc = new URL("file://" + file.getAbsolutePath()).openConnection();
//            String mimetype = uc.getContentType();
//            MimetypesFIleTypeMap gives PNG as application/octet-stream, but it seems so does URLConnection
//            have to make dirty workaround
            if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
                mimetype = "image/png";
            } else {
                javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
                mimetype  = mtMap.getContentType(file);
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
}
