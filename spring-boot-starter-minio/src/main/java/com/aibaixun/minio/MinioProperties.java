package com.aibaixun.minio;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
@ConfigurationProperties(
        prefix = "bx.minio"
)
public class MinioProperties {

    private String endPoint;
    private String bucketName;
    private String accessKey;
    private String secretKey;
    private boolean enable;

    public MinioProperties() {
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getEndPoint() {
        return this.endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getAccessKey() {
        return this.accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String toString() {
        return "MinioProperties{endPoint='" + this.endPoint + '\'' + ", bucketName='" + this.bucketName + '\'' + ", enable=" + this.enable + '}';
    }
}
