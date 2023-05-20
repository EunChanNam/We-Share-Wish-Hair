package com.inq.wishhair.wesharewishhair.review.service.dto.response;

import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.dto.response.ResponseWrapper;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.infra.query.dto.response.ReviewQueryResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

import static com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponseAssembler.*;
import static com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponseAssembler.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ReviewResponseAssembler {

    public static PagedResponse<ReviewResponse> toPagedReviewResponse(Slice<ReviewQueryResponse> slice) {
        return new PagedResponse<>(transferContentToResponse(slice));
    }

    private static Slice<ReviewResponse> transferContentToResponse(Slice<ReviewQueryResponse> slice) {
        return slice.map(ReviewResponseAssembler::toReviewResponse);
    }

    public static ReviewResponse toReviewResponse(ReviewQueryResponse queryResponse) {
        Review review = queryResponse.getReview();

        return ReviewResponse.builder()
                .reviewId(review.getId())
                .hairStyleName(review.getHairStyle().getName())
                .userNickname(review.getWriter().getNicknameValue())
                .score(review.getScore().getValue())
                .contents(review.getContentsValue())
                .createdDate(review.getCreatedDate())
                .photos(toPhotoResponses(review.getPhotos()))
                .likes(queryResponse.getLikes())
                .hashTags(toHashTagResponses(review.getHairStyle().getHashTags()))
                .writerId(review.getWriter().getId())
                .build();
    }

    public static ReviewDetailResponse toReviewDetailResponse(ReviewQueryResponse queryResponse, boolean isLiking) {
        return new ReviewDetailResponse(toReviewResponse(queryResponse), isLiking);
    }

    public static ResponseWrapper<ReviewSimpleResponse> toWrappedSimpleResponse(List<Review> reviews) {
        List<ReviewSimpleResponse> responses = reviews.stream().map(ReviewSimpleResponse::new).toList();
        return new ResponseWrapper<>(responses);
    }

    public static ResponseWrapper<ReviewResponse> toWrappedReviewResponse(List<ReviewQueryResponse> responses) {
        List<ReviewResponse> reviewResponses = responses.stream().map(ReviewResponseAssembler::toReviewResponse).toList();
        return new ResponseWrapper<>(reviewResponses);
    }
}
