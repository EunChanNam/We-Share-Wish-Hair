package com.inq.wishhair.wesharewishhair.review.service.dto.response;

import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponseAssembler;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HashTagResponse;
import com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponseAssembler;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewResponse {

    private Long reviewId;

    private String hairStyleName;

    private String userNickname;

    private String score;

    private String contents;

    private LocalDateTime createdDate;

    private List<PhotoResponse> photos;

    private int likes;

    private List<HashTagResponse> hashTags;

    public ReviewResponse(Review review) {
        this.reviewId = review.getId();
        this.hairStyleName = review.getHairStyle().getName();
        this.userNickname = review.getUser().getName();
        this.score = review.getScore().getValue();
        this.contents = review.getContentsValue();
        this.createdDate = review.getCreatedDate();
        this.likes = review.getLikes();
        this.photos = PhotoResponseAssembler.toPhotoResponses(review.getPhotos());
        this.hashTags = HairStyleResponseAssembler.toHashTagResponses(review.getHairStyle().getHashTags());
    }
}
