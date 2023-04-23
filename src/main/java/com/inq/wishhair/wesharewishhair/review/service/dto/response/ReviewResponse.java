package com.inq.wishhair.wesharewishhair.review.service.dto.response;

import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponseAssembler;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HashTagResponse;
import com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponseAssembler;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponse;
import com.inq.wishhair.wesharewishhair.review.infra.query.dto.response.ReviewQueryResponse;
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

    private boolean isWriter;

    public ReviewResponse(ReviewQueryResponse queryResponse, Long userId) {
        this.reviewId = queryResponse.getReview().getId();
        this.hairStyleName = queryResponse.getReview().getHairStyle().getName();
        this.userNickname = queryResponse.getReview().getUser().getName();
        this.score = queryResponse.getReview().getScore().getValue();
        this.contents = queryResponse.getReview().getContentsValue();
        this.createdDate = queryResponse.getReview().getCreatedDate();
        this.likes = queryResponse.getReview().getLikes();
        this.photos = PhotoResponseAssembler.toPhotoResponses(queryResponse.getReview().getPhotos());
        this.hashTags = HairStyleResponseAssembler.toHashTagResponses(queryResponse.getReview().getHairStyle().getHashTags());
        this.isWriter = queryResponse.getReview().isWriter(userId);
    }
}
