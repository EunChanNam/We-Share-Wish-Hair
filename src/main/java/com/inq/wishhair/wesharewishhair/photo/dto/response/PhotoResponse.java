package com.inq.wishhair.wesharewishhair.photo.dto.response;

import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

@Getter
@AllArgsConstructor
public class PhotoResponse {

    private final String encodedImage;

    public PhotoResponse(Photo photo) {
        String fullPath = FileDir.VALUE + photo.getStoreFilename();
        File file = new File(fullPath);

        byte[] imageBytes = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(imageBytes);
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        encodedImage = Base64.getEncoder().encodeToString(imageBytes);
    }
}
