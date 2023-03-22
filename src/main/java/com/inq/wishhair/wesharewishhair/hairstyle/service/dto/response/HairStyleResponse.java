package com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.HashTag;
import com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponse;
import com.inq.wishhair.wesharewishhair.photo.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

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
        this.photos = generatePhotoResponses(hairStyle.getPhotos());
        this.hashTags = generateHashTagResponses(hairStyle.getHashTags());
    }

    private List<HashTagResponse> generateHashTagResponses(List<HashTag> hashTags) {
        return hashTags.stream()
                .map(HashTagResponse::new)
                .toList();
    }

    private List<PhotoResponse> generatePhotoResponses(List<Photo> photos) {
        return photos.stream()
                .map(PhotoResponse::new)
                .toList(); //지연로딩
    }
}
