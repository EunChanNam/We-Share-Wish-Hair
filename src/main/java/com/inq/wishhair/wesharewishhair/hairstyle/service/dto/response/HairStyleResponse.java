package com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponse;
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
        //지연 로딩
        this.photos = hairStyle.getPhotos()
                .stream().map(PhotoResponse::new)
                .toList();
    }
}
