package com.inq.wishhair.wesharewishhair.web.photo.dto.response;

import com.inq.wishhair.wesharewishhair.domain.photo.HairPhoto;
import lombok.Getter;

@Getter
public class PhotoResponse {

    private final String storeFilename;

    public PhotoResponse(HairPhoto photo) {
        this.storeFilename = photo.getStoreFilename();
    }
}
