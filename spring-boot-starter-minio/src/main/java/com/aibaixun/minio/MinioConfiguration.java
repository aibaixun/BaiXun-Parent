package com.aibaixun.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
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
            minioClient =  MinioClient.builder().endpoint(minioProperties.getEndPoint()).credentials(minioProperties.getAccessKey(),minioProperties.getSecretKey()).build();
            boolean isExist = minioClient.bucketExists( BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (!isExist) {
                var bucketName = minioProperties.getBucketName();
                String policyJson = "{\n" +
                        "\t\"Version\": \"2012-10-17\",\n" +
                        "\t\"Statement\": [{\n" +
                        "\t\t\"Effect\": \"Allow\",\n" +
                        "\t\t\"Principal\": {\n" +
                        "\t\t\t\"AWS\": [\"*\"]\n" +
                        "\t\t},\n" +
                        "\t\t\"Action\": [\"s3:GetBucketLocation\", \"s3:ListBucket\", \"s3:ListBucketMultipartUploads\"],\n" +
                        "\t\t\"Resource\": [\"arn:aws:s3:::" + bucketName + "\"]\n" +
                        "\t}, {\n" +
                        "\t\t\"Effect\": \"Allow\",\n" +
                        "\t\t\"Principal\": {\n" +
                        "\t\t\t\"AWS\": [\"*\"]\n" +
                        "\t\t},\n" +
                        "\t\t\"Action\": [\"s3:AbortMultipartUpload\", \"s3:DeleteObject\", \"s3:GetObject\", \"s3:ListMultipartUploadParts\", \"s3:PutObject\"],\n" +
                        "\t\t\"Resource\": [\"arn:aws:s3:::" + bucketName + "/*\"]\n" +
                        "\t}]\n" +
                        "}\n";
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucketName).config(policyJson).build());

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
