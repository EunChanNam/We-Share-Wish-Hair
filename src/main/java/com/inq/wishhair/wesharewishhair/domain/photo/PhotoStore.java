package com.inq.wishhair.wesharewishhair.domain.T;

import com.inq.wishhair.wesharewishhair.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.exception.WishHairException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PhotoStore<T> {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public T storeFile(MultipartFile multipartFile) {
        if (!multipartFile.isEmpty()) {
            String originalFilename = multipartFile.getOriginalFilename();
            String storeFilename = createStoreFilename(originalFilename);
            String fullPath = getFullPath(storeFilename);

            //IOException 잡아서 사용자 정의 Exception 으로 변경
            try {
                multipartFile.transferTo(new File(fullPath));
            } catch (IOException e) {
                throw new WishHairException(ErrorCode.FILE_TRANSFER_EX);
            }

            //TODO 응답부분 마저 작성하기
        } else throw new WishHairException(ErrorCode.EMPTY_FILE_EX);
    }

    public List<T> storeFiles(List<MultipartFile> multipartFiles) {
        if (multipartFiles == null) return new ArrayList<>(); // 사진을 저장하지 않는경우 null 방지
        List<T> resultList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            T photo = storeFile(multipartFile);
            resultList.add(photo);
        }
        return resultList;
    }

    private String createStoreFilename(String originalFilename) {
        String ext = getExt(originalFilename);
        return UUID.randomUUID().toString() + ext;
    }

    private String getExt(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        return originalFilename.substring(index);
    }
}