package com.inq.wishhair.wesharewishhair.review.service.dto.response;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.HashTag;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HashTagResponse;
import com.inq.wishhair.wesharewishhair.photo.entity.Photo;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewResponse {

    private String hairStyleName;

    private String userNickName;

    private String score;

    private List<PhotoResponse> photos;

    private int likes;

    private List<HashTagResponse> hasTags;

    public ReviewResponse(Review review) {
        this.hairStyleName = review.getHairStyle().getName();
        this.userNickName = review.getUser().getName();
        this.score = review.getScore().getValue();
        this.likes = review.getLikes();
        this.photos = generatePhotoResponses(review.getPhotos()); //지연로딩
        this.hasTags = generateHashTagResponses(review.getHairStyle().getHashTags());
    }

    private List<HashTagResponse> generateHashTagResponses(List<HashTag> hashTags) {
        return hashTags.stream().map(HashTagResponse::new).toList();
    }

    private static List<PhotoResponse> generatePhotoResponses(List<Photo> photos) {
        return photos.stream().map(PhotoResponse::new).toList();
    }
}
