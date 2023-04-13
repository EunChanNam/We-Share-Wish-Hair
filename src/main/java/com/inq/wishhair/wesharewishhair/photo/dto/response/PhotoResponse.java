package com.inq.wishhair.wesharewishhair.photo.dto.response;

import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

@Getter
@AllArgsConstructor
public class PhotoResponse {

    private final String encodedImage;

    public PhotoResponse(Photo photo) {
        String filePath = FilePath.VALUE;
        File image = new File(filePath + photo.getStoreFilename());

        byte[] byteImage = new byte[(int) image.length()];

        try {
            FileInputStream inputStream = new FileInputStream(image);
            inputStream.read(byteImage);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.encodedImage = Base64.getEncoder().encodeToString(byteImage);
    }
}
