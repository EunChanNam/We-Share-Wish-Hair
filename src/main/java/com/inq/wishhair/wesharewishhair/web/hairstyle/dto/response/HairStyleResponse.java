package com.inq.wishhair.wesharewishhair.web.hairstyle.dto.response;

import com.inq.wishhair.wesharewishhair.domain.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.photo.Photo;
import lombok.Getter;

import java.util.List;

@Getter
public class HairStyleResponse {

    private String name;

    private List<PhotoResponse> photos;

    public HairStyleResponse(HairStyle hairStyle) {
        this.name = hairStyle.getName();
        if (hairStyle.getHasPhotos()) {
            this.photos = hairStyle.getPhotos()
                    .stream().map(PhotoResponse::new)
                    .toList();
        }
    }
}
