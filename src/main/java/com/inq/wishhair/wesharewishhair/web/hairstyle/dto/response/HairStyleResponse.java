package com.inq.wishhair.wesharewishhair.web.hairstyle.dto.response;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.web.photo.dto.response.PhotoResponse;
import jakarta.persistence.Persistence;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class HairStyleResponse {

    private Long hairStyleId;

    private String name;

    private List<PhotoResponse> photos;

    public HairStyleResponse(HairStyle hairStyle) {
        this.hairStyleId = hairStyle.getId();
        this.name = hairStyle.getName();
        if (Persistence.getPersistenceUtil().isLoaded(hairStyle.getPhotos())) {
            this.photos = hairStyle.getPhotos()
                    .stream().map(PhotoResponse::new)
                    .toList();
        }
    }
}
