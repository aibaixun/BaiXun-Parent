package com.aibaixun.minio;

import io.minio.MinioClient;
import io.minio.policy.PolicyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
@Configuration
@EnableConfigurationProperties({MinioProperties.class})
@ConditionalOnProperty(
        prefix = "goff-storage.minio",
        value = {"enable"},
        havingValue = "true"
)
public class MinioConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(MinioConfiguration.class);

    public MinioConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean
    MinioClient minioClient(MinioProperties minioProperties) {
        logger.info("minio parameter [{}]", minioProperties.toString());
        MinioClient minioClient = null;

        try {
            minioClient = new MinioClient(minioProperties.getEndPoint(), minioProperties.getAccessKey(), minioProperties.getSecretKey());
            boolean isExist = minioClient.bucketExists(minioProperties.getBucketName());
            if (!isExist) {
                minioClient.makeBucket(minioProperties.getBucketName());
                minioClient.setBucketPolicy(minioProperties.getBucketName(), "*.*", PolicyType.READ_ONLY);
            }

            return minioClient;
        } catch (Exception var4) {
            throw new RuntimeException("initial minio fail !", var4);
        }
    }

    @Bean
    @ConditionalOnClass({HttpServletResponse.class, MultipartFile.class})
    AttachmentManager attachmentManager(MinioClient minioClient, MinioProperties minioProperties) {
        AttachmentManager attachmentManager = new AttachmentManager(minioClient);
        attachmentManager.setBucketName(minioProperties.getBucketName());
        return attachmentManager;
    }
}
