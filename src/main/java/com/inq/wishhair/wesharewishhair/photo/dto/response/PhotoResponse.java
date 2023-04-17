package com.inq.wishhair.wesharewishhair.photo.dto.response;

import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class PhotoResponse {

    private final String storeUrl;

    public PhotoResponse(Photo photo) {
        this.storeUrl = photo.getStoreUrl();
    }
}
