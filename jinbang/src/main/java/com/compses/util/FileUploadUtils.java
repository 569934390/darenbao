package com.compses.util;

import java.io.*;

/**
 * Created by nini on 2016/3/8.
 */
public class FileUploadUtils {

    static int FILE_SIZE = 1024;

    public void upLoadFile(File source, File target) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(source), FILE_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(target),
                    FILE_SIZE);
            byte[] image = new byte[FILE_SIZE];
            while (in.read(image) > 0) {
                out.write(image);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException ex) {

            }
        }
    }
}
