package com.inq.wishhair.wesharewishhair.photo.dto.response;

import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
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
