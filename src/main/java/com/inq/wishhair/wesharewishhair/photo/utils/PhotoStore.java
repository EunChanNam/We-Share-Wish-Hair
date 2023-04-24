package com.inq.wishhair.wesharewishhair.photo.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class PhotoStore {

    private final AmazonS3Client amazonS3Client;
    private final String buketName;

    public PhotoStore(AmazonS3Client amazonS3Client,
                      @Value("${cloud.aws.s3.bucket}") String buketName) {
        this.amazonS3Client = amazonS3Client;
        this.buketName = buketName;
    }

    public List<String> uploadFiles(List<MultipartFile> files) {
        List<String> storeUrls = new ArrayList<>();
        files.forEach(file -> storeUrls.add(uploadFile(file)));
        return storeUrls;
    }

    private String uploadFile(MultipartFile file) {
        validateFileExist(file);

        String originalFilename = file.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try (InputStream inputStream = file.getInputStream();) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    buketName,
                    storeFilename,
                    inputStream,
                    metadata).withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3Client.putObject(putObjectRequest);

            return amazonS3Client.getUrl(buketName, storeFilename).toString();
        } catch (IOException e) {
            throw new WishHairException(ErrorCode.FILE_TRANSFER_EX);
        }
    }

    private void validateFileExist(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new WishHairException(ErrorCode.EMPTY_FILE_EX);
        }
    }

    private String createStoreFilename(String originalFilename) {
        String ext = getExt(originalFilename);
        return UUID.randomUUID() + ext;
    }

    private String getExt(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        return originalFilename.substring(index);
    }
}