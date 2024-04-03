package com.ssafy.ibalance.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.ibalance.child.exception.NotImageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Util {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.url}")
    private String s3Url;

    private final AmazonS3 amazonS3;
    private final ImageStoreUtil imageStoreUtil;

    public String uploadImage(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String extension = file.getContentType();
        imageStoreUtil.checkImage(originalFileName, extension);

        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFileName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(extension);
        metadata.setContentLength(file.getSize());

        try {
            amazonS3.putObject(bucket, s3FileName, file.getInputStream(), metadata);
        } catch (IOException e) {
            log.warn("S3 업로드 중 IOException 발생 : {}", e.getMessage());
        }

        return s3Url + s3FileName;
    }
}
