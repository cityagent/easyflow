package org.lecoder.easyflow.modules.flowlong.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.lecoder.easyflow.modules.flowlong.service.IFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Administrator
 */
@Service
public class FileServiceImpl implements IFileService {
    @Resource
    private MinioClient minioClient;
    @Value("${minio.bucket-name}")
    private String bucketName;
    @Value("${minio.url}")
    private String minioUrl;

    @Override
    public String uploadFile(File file, String objectName) {
        try (FileInputStream fis = new FileInputStream(file)) {
            // 创建PutObjectArgs对象
            // 获取文件的原始后缀名
            String originalFileName = file.getName();
            String extension = "";
            int i = originalFileName.lastIndexOf('.');
            if (i > 0) {
                extension = originalFileName.substring(i);
            }
            // 将后缀名添加到对象名称中
            objectName += extension;
             // 这里可以根据文件类型设置不同的contentType
            PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(fis, file.length(), -1).contentType("application/octet-stream").build();
            // 执行上传操作
            minioClient.putObject(putObjectArgs);
            // 获取并返回文件URL
            return constructFileUrl(bucketName, objectName);
        } catch (IOException | MinioException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException("文件上传失败: " + e.getMessage(), e);
        }
    }

    private String constructFileUrl(String bucketName, String objectName) {
        try {
            String encodedObjectName = URLEncoder.encode(objectName, StandardCharsets.UTF_8.toString()).replace("+", "%20");
            return minioUrl + bucketName + "/" + encodedObjectName;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("URL编码失败", e);
        }
    }

}
