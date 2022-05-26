package com.aibaixun.basic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class Base64Img {

    public Base64Img() {
    }

    public static String GetImageStr(File imgFile) {
        InputStream in = null;
        byte[] data = null;

        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return Base64StrUtil.string2Base64(data);
    }
}
