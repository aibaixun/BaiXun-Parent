package com.aibaixun.minio;

import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.policy.PolicyType;
import com.aibaixun.basic.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class AttachmentManager {

    private static final Logger logger = LoggerFactory.getLogger(AttachmentManager.class);
    private MinioClient minioClient;
    private String bucketName;

    public AttachmentManager() {
    }

    public AttachmentManager(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public boolean uploadFile(String dir, String fileName, MultipartFile multipartFile, boolean allowReplace) {
        boolean result = false;
        if (!StringUtil.isEmpty(dir)) {
            fileName = dir + "/" + fileName;
        }

        try {
            if (!allowReplace) {
                try {
                    this.minioClient.statObject(this.bucketName, fileName);
                    logger.error("file {} already exists in {}", fileName, this.bucketName);
                    return false;
                } catch (ErrorResponseException var7) {
                    logger.info("file {} not exists in {}", fileName, this.bucketName);
                }
            }

            boolean isExist = this.minioClient.bucketExists(this.bucketName);
            if (!isExist) {
                this.minioClient.makeBucket(this.bucketName);
                this.minioClient.setBucketPolicy(this.bucketName, "*.*", PolicyType.READ_ONLY);
            }

            this.minioClient.putObject(this.bucketName, fileName, multipartFile.getInputStream(), multipartFile.getContentType());
            logger.info("uploadFile {} to {} success!", fileName, this.bucketName);
            result = true;
        } catch (Exception var8) {
            logger.error("uploadFile {} to {} error:", new Object[]{fileName, this.bucketName, var8});
        }

        return result;
    }

    public boolean deleteFile(String dir, String fileName) {
        boolean result = false;
        if (!StringUtil.isEmpty(dir)) {
            fileName = dir + "/" + fileName;
        }

        try {
            this.minioClient.removeObject(this.bucketName, fileName);
            logger.info("deleteFile {} from {} success!", fileName, this.bucketName);
            result = true;
        } catch (Exception var5) {
            logger.error("deleteFile {} from {} error:", new Object[]{fileName, this.bucketName, var5});
        }

        return result;
    }

    public boolean downloadFile(String dir, String fileName, HttpServletResponse response, boolean attachment) {
        boolean result = false;
        if (!StringUtil.isEmpty(dir)) {
            fileName = dir + "/" + fileName;
        }

        try {
            InputStream file = this.minioClient.getObject(this.bucketName, fileName);
            Throwable fileThrowable = null;

            try {
                ServletOutputStream servletOutputStream = response.getOutputStream();
                Throwable inputThrowable = null;

                try {
                    String filename = new String(fileName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                    if (!attachment) {
                        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                    }

                    byte[] buffer = new byte[1024];

                    int len;
                    while((len = file.read(buffer)) > 0) {
                        servletOutputStream.write(buffer, 0, len);
                    }

                    servletOutputStream.flush();
                    logger.info("downloadFile {} from {} success!", fileName, this.bucketName);
                    result = true;
                } catch (Throwable var36) {
                    inputThrowable = var36;
                    throw var36;
                } finally {
                    if (servletOutputStream != null) {
                        if (inputThrowable != null) {
                            try {
                                servletOutputStream.close();
                            } catch (Throwable var35) {
                                inputThrowable.addSuppressed(var35);
                            }
                        } else {
                            servletOutputStream.close();
                        }
                    }

                }
            } catch (Throwable t) {
                fileThrowable = t;
                throw t;
            } finally {
                if (file != null) {
                    if (fileThrowable != null) {
                        try {
                            file.close();
                        } catch (Throwable var34) {
                            fileThrowable.addSuppressed(var34);
                        }
                    } else {
                        file.close();
                    }
                }

            }
        } catch (Exception e) {
            logger.error("downloadFile {} {}from {} error:", fileName, this.bucketName, e);
        }

        return result;
    }

    public boolean downloadFile(String dir, String fileName, HttpServletResponse response) {
        return this.downloadFile(dir, fileName, response, false);
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

}
