package com.inq.wishhair.wesharewishhair.web.hairstyle.dto.response;

import com.inq.wishhair.wesharewishhair.domain.hairstyle.photo.Photo;
import lombok.Getter;

@Getter
public class PhotoResponse {

    private final String storeFilename;

    public PhotoResponse(Photo photo) {
        this.storeFilename = photo.getStoreFilename();
    }
}
