package com.inq.wishhair.wesharewishhair.review.service.dto.response;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.HashTag;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HashTagResponse;
import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponse;
import jakarta.persistence.Persistence;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewResponse {

    private String hairStyleName;

    private String userNickName;

    private String score;

    private String contents;

    private LocalDateTime createdDate;

    private List<PhotoResponse> photos;

    private int likes;

    private List<HashTagResponse> hasTags;

    public ReviewResponse(Review review, boolean isPhotoLoaded) {
        this.hairStyleName = review.getHairStyle().getName();
        this.userNickName = review.getUser().getName();
        this.score = review.getScore().getValue();
        this.contents = review.getContents();
        this.createdDate = review.getCreatedDate();
        this.likes = review.getLikes();
        //fetch join 을 통해 미리 당겨온 경우엔 사진이 없을때 추가 쿼리를 방지
        if (isPhotoLoaded) {
            if (Persistence.getPersistenceUtil().isLoaded(photos)) {
                this.photos = generatePhotoResponses(review.getPhotos());
            }
        } else this.photos = generatePhotoResponses(review.getPhotos());
        this.hasTags = generateHashTagResponses(review.getHairStyle().getHashTags());
    }

    private List<HashTagResponse> generateHashTagResponses(List<HashTag> hashTags) {
        return hashTags.stream().map(HashTagResponse::new).toList();
    }

    private static List<PhotoResponse> generatePhotoResponses(List<Photo> photos) {
        return photos.stream().map(PhotoResponse::new).toList();
    }
}
