package com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HashTagResponse;
import com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponseAssembler.*;
import static com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponseAssembler.toPhotoResponses;

@Getter
@AllArgsConstructor
public class WishListResponse {

    private Long hairStyleId;

    private String hairStyleName;

    private List<PhotoResponse> photos;

    private List<HashTagResponse> hashTags;

    public WishListResponse(HairStyle hairStyle) {
        this.hairStyleId = hairStyle.getId();
        this.hairStyleName = hairStyle.getName();
        this.photos = toPhotoResponses(hairStyle.getPhotos());
        this.hashTags = toHashTagResponses(hairStyle.getHashTags());
    }
}
