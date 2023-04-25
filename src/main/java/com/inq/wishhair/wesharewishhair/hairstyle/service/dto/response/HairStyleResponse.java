package com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponseAssembler.toHashTagResponses;
import static com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponseAssembler.toPhotoResponses;

@Getter
@AllArgsConstructor
public class HairStyleResponse {

    private Long hairStyleId;

    private String name;

    private List<PhotoResponse> photos;

    private List<HashTagResponse> hashTags;

    public HairStyleResponse(HairStyle hairStyle) {
        this.hairStyleId = hairStyle.getId();
        this.name = hairStyle.getName();
        this.photos = toPhotoResponses(hairStyle.getPhotos());
        this.hashTags = toHashTagResponses(hairStyle.getHashTags());
    }
}
