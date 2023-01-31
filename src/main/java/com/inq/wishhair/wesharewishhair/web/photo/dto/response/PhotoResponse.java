package com.inq.wishhair.wesharewishhair.web.photo.dto.response;

import com.inq.wishhair.wesharewishhair.domain.photo.entity.Photo;
import lombok.Getter;

@Getter
public class PhotoResponse {

    private final String storeFilename;

    public PhotoResponse(Photo photo) {
        this.storeFilename = photo.getStoreFilename();
    }
}
