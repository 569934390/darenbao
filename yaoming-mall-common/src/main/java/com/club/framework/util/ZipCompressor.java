package com.club.framework.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.club.framework.log.ClubLogManager;
import com.club.web.common.controller.DownloadController;
/**
 * 文件压缩工具类
 * @author hu.bo<br>
 * @version 1.0<br>
 * @CreateDate 2015-12-6 <br>
 * @since V1.0<br>
 * @see com.club.framework.util <br>
 */
public class ZipCompressor {
    private static final ClubLogManager logger = ClubLogManager.getLogger(DownloadController.class);
    static final int BUFFER = 8192;
    private File zipFile;

    public ZipCompressor(String pathName) {
        zipFile = new File(pathName);
    }

    public File getZipFile() {
        return zipFile;
    }

    /**
     * 文件压缩处理函数
     * @author  hu.bo<br>
     * @param pathName 待压缩的文件(或文件夹)
     */
    public void compress(List<String> pathName) {
        ZipOutputStream out = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
            out = new ZipOutputStream(cos);
            String basedir = "";
            for (int i = 0; i < pathName.size(); i++) {
                compress(new File(pathName.get(i)), out, basedir);
            }
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 文件压缩处理函数
     * @author  hu.bo<br>
     * @param files 待压缩的文件
     * @param realNames 待压缩文件的真实文件名
     */
    public void compress(List<String> files,List<String> realNames,String basedir) {
        ZipOutputStream out = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
            out = new ZipOutputStream(cos);
            for (int i = 0; i < files.size(); i++) {
                compressFile2(new File(basedir + files.get(i)), out, realNames.get(i));
            }
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private void compress(File file, ZipOutputStream out, String basedir) {
        /* 判断是目录还是文件 */
        if (file.isDirectory()) {
            logger.debug("压缩：" + basedir + file.getName());
            this.compressDirectory(file, out, basedir);
        } else {
            logger.debug("压缩：" + basedir + file.getName());
            this.compressFile(file, out, basedir);
        }
    }

    /** 压缩一个目录 */
    private void compressDirectory(File dir, ZipOutputStream out, String basedir) {
        if (!dir.exists())
            return;

        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            /* 递归 */
            compress(files[i], out, basedir + dir.getName() + "/");
        }
    }

    /** 压缩一个文件 */
    private void compressFile(File file, ZipOutputStream out, String basedir) {
        if (!file.exists()) {
            return;
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(basedir + file.getName());
            out.putNextEntry(entry);
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = bis.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            bis.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** 压缩一个文件 */
    private void compressFile2(File file, ZipOutputStream out, String realName) {
        if (!file.exists()) {
            return;
        }
        if(Utils.isEmpty(realName)){
            realName = file.getName();
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(realName);
            out.putNextEntry(entry);
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = bis.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            bis.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ZipCompressor zc = new ZipCompressor("E:/log/测试5.zip");
        List<String> files = new ArrayList<String>();
        files.add("E:/baiduyundownload/穿衣搭配有讲究_让男士更有魅力.doc");
        files.add("E:/log2/club_job.log");
        List<String> realNames = new ArrayList<String>();
        realNames.add("测试穿衣.doc");
        realNames.add("测试job.log");
//        zc.compress("E:/log2", "E:/baiduyundownload/粥的花样做法");
        zc.compress(files, realNames,"");
//        String filename = "测试.穿衣.doc";
//        int index = filename.lastIndexOf(".");
//        filename = filename.substring(0, index);
        System.out.println(zc.getZipFile().getName());
    }
}