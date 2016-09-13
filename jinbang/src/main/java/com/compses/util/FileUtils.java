package com.compses.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


/**
 * 文件处理工具类
 */
public class FileUtils {
	private static final Logger logger = Logger.getLogger(FileUtils.class);
	
    private FileUtils() {
    }

    /**
     * 存储字节数组到文件

     * @param fileName 文件名

     * @param data 数据
     * @throws IOException
     */
    public static void save(String fileName, byte[] data)
        throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName);
            fos.write(data);
            fos.flush();
        }
        finally {
            if (fos != null) {
                try {
                    fos.close();
                }
                catch (IOException ex) {

                }
            }
        }
    }

    /**
     * 导入文件到字节数组

     * @param fileName 文件名

     * @return 字节数组
     * @throws IOException
     */
    public static byte[] load(String fileName)
        throws IOException {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            fis = new FileInputStream(fileName);
            baos = new ByteArrayOutputStream();
            byte[] temp = new byte[8192];
            int count = fis.read(temp);
            while ( count > 0 ) {
                baos.write(temp,0,count);
                count = fis.read(temp);
            }
            return baos.toByteArray();
        }
        finally {
            if (baos != null) {
                try {
                    baos.close();
                }
                catch (IOException ex) {
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (IOException ex) {
                }
            }
        }
    }
    
    /**
     * 追加方式写文件
     * @param content
     * @param filename
     */
    public static void appendSave(String content, String filename) {
    	try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(filename, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    /**
     * 移文件
     * @param srcPath 源文件完整路径
     * @param destpath 目标目录路径
     * @throws BaseAppException
     * @throws Exception
     */
	public static void moveFile(String srcPath, String destpath) throws Exception {
		/**
		 * 建立目标路径目录对象
		 */
		File pathdir = new File(destpath);
		/**
		 * 若目录不存在或非目录，创建一个
		 */
		if (!pathdir.exists() || !pathdir.isDirectory()) {
			boolean flag = pathdir.mkdirs();
            if (!flag) return ;
		}
		File srcfile = new File(srcPath);
		if (!srcfile.exists() || !srcfile.isFile()) {
			logger.error("The file is not exist.");
			return;
		}

		File file = new File(destpath + File.separator + srcfile.getName());
		boolean flag = srcfile.renameTo(file);
        if (!flag)return;
	}
	
	/**
	 * 导出文件
	 * @param request
	 * @param response
	 * @param content 文件内容
	 * @param fileName 文件名称
	 * @throws IOException 
	 */
	public static void exportFile(HttpServletRequest request,HttpServletResponse response,String content,String fileName) throws IOException{
		ServletOutputStream out= response.getOutputStream();
		byte[] bytes=content.getBytes();
		response.setHeader("Content-Disposition", "inline;filename="+URLEncoder.encode(fileName,"UTF-8"));
		response.setHeader("Content-Length", "" + bytes.length);
		response.setContentType("application/octet-stream");
		out.write(bytes);
		out.flush();
		out.close();
	}
}
