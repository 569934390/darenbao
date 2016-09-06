package com.club.framework.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 
 * @ClassName: FileTypeUtil 
 * @Description: 判断文件类型工具类 
 */
public class FileTypeUtil {
	// 存储文件类型的MAP
	public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

	private FileTypeUtil() {
	}

	static {
		getAllFileTypes(); // 初始化文件类型信息
	}

	/**
	 * 
	 * @Title: getAllFileTypes 
	 * @Description: 获取所有支持的文件类型 
	 * @return void   
	 * @throws 
	 */
	private static void getAllFileTypes() {
		//DOCX、CSV、XLSX、PPT、PPTX、TXT
		
//		FILE_TYPE_MAP.put("txt", ""); // txt
//		FILE_TYPE_MAP.put("pptx", ""); // pptx
//		FILE_TYPE_MAP.put("ppt", ""); // ppt
//		FILE_TYPE_MAP.put("xlsx", ""); // xlsx
//		FILE_TYPE_MAP.put("csv", ""); // csv
//		FILE_TYPE_MAP.put("docx", ""); // docx
		
		FILE_TYPE_MAP.put("jpg", "FFD8FF"); // JPEG (jpg)
		FILE_TYPE_MAP.put("jpeg", "FFD8FF"); // JPEG (jpg)
		FILE_TYPE_MAP.put("png", "89504E47"); // PNG (png)
		FILE_TYPE_MAP.put("gif", "47494638"); // GIF (gif)
		FILE_TYPE_MAP.put("tif", "49492A00"); // TIFF (tif)
		FILE_TYPE_MAP.put("bmp", "424D"); // Windows Bitmap (bmp)
		FILE_TYPE_MAP.put("dwg", "41433130"); // CAD (dwg)
		FILE_TYPE_MAP.put("html", "68746D6C3E"); // HTML (html)
		FILE_TYPE_MAP.put("rtf", "7B5C727466"); // Rich Text Format (rtf)
		FILE_TYPE_MAP.put("xml", "3C3F786D6C");
		FILE_TYPE_MAP.put("zip", "504B0304");
		FILE_TYPE_MAP.put("rar", "52617221");
		FILE_TYPE_MAP.put("psd", "38425053"); // Photoshop (psd)
		FILE_TYPE_MAP.put("eml", "44656C69766572792D646174653A"); // Email
																	// [thorough
																	// only]
																	// (eml)
		FILE_TYPE_MAP.put("dbx", "CFAD12FEC5FD746F"); // Outlook Express (dbx)
		FILE_TYPE_MAP.put("pst", "2142444E"); // Outlook (pst)
		FILE_TYPE_MAP.put("xls", "D0CF11E0"); // MS Word
		FILE_TYPE_MAP.put("doc", "D0CF11E0"); // MS Excel 注意：word 和 excel的文件头一样
		FILE_TYPE_MAP.put("mdb", "5374616E64617264204A"); // MS Access (mdb)
		FILE_TYPE_MAP.put("wpd", "FF575043"); // WordPerfect (wpd)
		FILE_TYPE_MAP.put("eps", "252150532D41646F6265");
		FILE_TYPE_MAP.put("ps", "252150532D41646F6265");
		FILE_TYPE_MAP.put("pdf", "255044462D312E"); // Adobe Acrobat (pdf)
		FILE_TYPE_MAP.put("qdf", "AC9EBD8F"); // Quicken (qdf)
		FILE_TYPE_MAP.put("pwl", "E3828596"); // Windows Password (pwl)
		FILE_TYPE_MAP.put("wav", "57415645"); // Wave (wav)
		FILE_TYPE_MAP.put("avi", "41564920");
		FILE_TYPE_MAP.put("ram", "2E7261FD"); // Real Audio (ram)
		FILE_TYPE_MAP.put("rm", "2E524D46"); // Real Media (rm)
		FILE_TYPE_MAP.put("mpg", "000001BA"); //
		FILE_TYPE_MAP.put("mov", "6D6F6F76"); // Quicktime (mov)
		FILE_TYPE_MAP.put("asf", "3026B2758E66CF11"); // Windows Media (asf)
		FILE_TYPE_MAP.put("mid", "4D546864"); // MIDI (mid)
	}

	/**
	 * 
	 * @Title: getImageFileType 
	 * @Description: 获取图片文件类型    
	 * @param f
	 * @return   
	 */
	public final static String getImageFileType(File f) {
		if (isImage(f)) {
			try {
				ImageInputStream iis = ImageIO.createImageInputStream(f);
				Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
				if (!iter.hasNext()) {
					return null;
				}
				ImageReader reader = iter.next();
				iis.close();
				return reader.getFormatName();
			} catch (IOException e) {
				return null;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title: getFileTypeByFile 
	 * @Description: 获取文件类型,包括图片,若格式不是已配置的,则返回null 
	 * @param file
	 * @return   
	 */
	public final static String getFileTypeByFile(File file) {
		String fileType = null;
		byte[] b = new byte[50];
		try {
			InputStream is = new FileInputStream(file);
			is.read(b);
			fileType = getFileTypeByStream(b);
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileType;
	}
	
	/**
	 * 
	 * @Title: getFileTypeByFile 
	 * @Description: 获取文件类型,包括图片,若格式不是已配置的,则返回null 
	 * @param CommonsMultipartFile
	 * @return   
	 */
	public final static String getFileTypeByFile(CommonsMultipartFile file) {
		String fileType = null;
		byte[] b = new byte[50];
		try {
			InputStream is = file.getInputStream();
			is.read(b);
			fileType = getFileTypeByStream(b);
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileType;
	}

	/**
	 * 
	 * @Title: getFileTypeByStream 
	 * @Description: 从文件流中获取文件类型(16进制文件类型)  
	 * @param b
	 * @return   
	 */
	public final static String getFileTypeByStream(byte[] b) {
		String fileTypeHex = String.valueOf(getFileHexString(b));
		Iterator<Entry<String, String>> entryIterator = FILE_TYPE_MAP.entrySet().iterator();
		while (entryIterator.hasNext()) {
			Entry<String, String> entry = entryIterator.next();
			String fileTypeHexValue = entry.getValue();
			if (fileTypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title: isImage 
	 * @Description: isImage,通过压缩文件来判断文件是否为图片
	 * @param file
	 * @return true 是 | false 否  
	 */
	public static final boolean isImage(File file) {
		boolean flag = false;
		try {
			BufferedImage bufReader = ImageIO.read(file);
			int width = bufReader.getWidth();
			int height = bufReader.getHeight();
			if (width == 0 || height == 0) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (IOException e) {
			flag = false;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 
	 * @Title: isImage 
	 * @Description: isImage,通过压缩文件来判断文件是否为图片
	 * @param CommonsMultipartFile
	 * @return true 是 | false 否  
	 */
	public static final boolean isImage02(CommonsMultipartFile file) {
		boolean flag = false;
		try {
			BufferedImage bufReader = ImageIO.read(file.getInputStream());
			int width = bufReader.getWidth();
			int height = bufReader.getHeight();
			if (width == 0 || height == 0) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (IOException e) {
			flag = false;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 
	 * @Title: getFileHexString 
	 * @Description: 获取文件16进制值  
	 * @param b
	 * @return   
	 */
	public final static String getFileHexString(byte[] b) {
		StringBuilder stringBuilder = new StringBuilder();

		if (b == null || b.length <= 0) {
			return null;
		}

		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 判断上传的文件的基本安全性
	 * @param file
	 * @return
	 */
	public final static boolean isOKInBasic(CommonsMultipartFile file){
		String fileType = file.getContentType().substring(file.getContentType().lastIndexOf("/")+1);
		boolean fileTypeIsOK = FileTypeUtil.FILE_TYPE_MAP.keySet().contains(fileType);
		String fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
		boolean fileNameIsNotOK = fileName.contains("../");
		String extensions = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
		boolean extensionsIsOK = FileTypeUtil.FILE_TYPE_MAP.keySet().contains(extensions.toLowerCase());
		if(fileTypeIsOK && extensionsIsOK && !fileNameIsNotOK)
			return true;
		return false;
	}
}
