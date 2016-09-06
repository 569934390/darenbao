package com.club.framework.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

//import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.club.core.spring.context.CustomPropertyConfigurer;
import com.club.web.common.Constants;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class DocumentExportUtils {

	public static void htmlToWord(String filePath) throws Exception {
        //拼一个标准的HTML格式文档
		File file = new File(filePath);
		String body = file2String(file, Constants.UTF_8_ENCODING);
        String content = "<html><head></head><body><div>" + body + "</div></body></html>";
        inputStreamToWord(content);
     }
	
     /**
      * 把is写入到对应的word输出流os中
      * 不考虑异常的捕获，直接抛出
      * @param is
      * @param os
      * @throws IOException
      */
     private static void inputStreamToWord(String content) throws IOException {
         byte b[] = content.getBytes(Constants.GB2312_ENCODING);
         ByteArrayInputStream bais = new ByteArrayInputStream(b);
         POIFSFileSystem poifs = new POIFSFileSystem();
         DirectoryEntry directory = poifs.getRoot();
         directory.createDocument("WordDocument", bais);
         String path = CustomPropertyConfigurer.getContextProperty("uploadPath").toString();
         OutputStream ostream = new FileOutputStream(path + "/tempFileForDeveloper.doc");
         poifs.writeFilesystem(ostream);
         ostream.flush();
         ostream.close();
         bais.close();
     }
     
     public static String file2String(File file, String charset) { 
         StringBuffer sb = new StringBuffer(); 
         try { 
                 LineNumberReader reader = new LineNumberReader(new BufferedReader(new InputStreamReader(new FileInputStream(file), charset))); 
                 String line; 
                 while ((line = reader.readLine()) != null) { 
                         sb.append(line).append(System.getProperty("line.separator")); 
                 } 
         } catch (UnsupportedEncodingException e) { 
                 System.out.println("读取文件为一个内存字符串失败，失败原因是使用了不支持的字符编码"); 
         } catch (FileNotFoundException e) { 
        	 System.out.println("读取文件为一个内存字符串失败，失败原因所给的文件" + file + "不存在！"); 
         } catch (IOException e) { 
        	 System.out.println("读取文件为一个内存字符串失败，失败原因是读取文件异常！"); 
         } 
         return sb.toString(); 
 } 
}