package com.aibaixun.basic.util;



import java.io.*;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class StreamUtil {

    public static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final int EOF = -1;

    public StreamUtil() {
    }

    public static byte[] stream2Bytes(InputStream is) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            copy(is, outputStream);
        } catch (IOException var3) {
            return null;
        }

        return outputStream.toByteArray();
    }

    public static InputStream cloneStream(InputStream is) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            copy(is, outputStream);
        } catch (IOException var3) {
            return null;
        }

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    public static long copy(InputStream in, OutputStream out) throws IOException {
        return copy(in, out, 8192);
    }

    public static long copy(InputStream in, OutputStream out, int bufferSize) throws IOException {
        if (bufferSize <= 0) {
            bufferSize = 8192;
        }

        byte[] buffer = new byte[bufferSize];
        long size = 0L;

        int readSize;
        while((readSize = in.read(buffer)) != -1) {
            out.write(buffer, 0, readSize);
            size += (long)readSize;
            out.flush();
        }

        return size;
    }
}
