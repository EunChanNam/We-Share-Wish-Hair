package com.inq.wishhair.wesharewishhair.web.photo.dto.response;

import com.inq.wishhair.wesharewishhair.photo.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PhotoResponse {

    private final String storeFilename;

    public PhotoResponse(Photo photo) {
        this.storeFilename = photo.getStoreFilename();
    }
}
