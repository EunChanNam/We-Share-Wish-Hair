package com.inq.wishhair.wesharewishhair.photo.dto.response;

import com.inq.wishhair.wesharewishhair.photo.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

@Getter
@AllArgsConstructor
public class PhotoResponse {

    private final String encodedFullPath;

    public PhotoResponse(Photo photo) {
        String fullPath = FileDir.VALUE + photo.getStoreFilename();

        try {
            FileInputStream fileInputStream = new FileInputStream(fullPath);

            file

            Base64.getEncoder().encode()
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
