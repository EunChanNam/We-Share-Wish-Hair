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

    public static PagedResponse<ReviewResponse> toPagedReviewResponse(Slice<ReviewQueryResponse> slice, Long userId) {
        return new PagedResponse<>(transferContentToResponse(slice, userId));
    }

    private static Slice<ReviewResponse> transferContentToResponse(Slice<ReviewQueryResponse> slice, Long userId) {
        return slice.map(review -> toReviewResponse(review, userId));
    }

    public static ReviewResponse toReviewResponse(ReviewQueryResponse queryResponse, Long userId) {
        Review review = queryResponse.getReview();
        return new ReviewResponse(
                review.getId(),
                review.getHairStyle().getName(),
                review.getWriter().getNicknameValue(),
                review.getScore().getValue(),
                review.getContents().getValue(),
                review.getCreatedDate(),
                toPhotoResponses(review.getPhotos()),
                queryResponse.getLikes(),
                toHashTagResponses(review.getHairStyle().getHashTags()),
                review.isWriter(userId)
        );
    }

    public static ReviewDetailResponse toReviewDetailResponse(ReviewQueryResponse queryResponse, Long userId, boolean isLiking) {
        return new ReviewDetailResponse(toReviewResponse(queryResponse, userId), isLiking);
    }

    public static ResponseWrapper<ReviewSimpleResponse> toWrappedSimpleResponse(List<Review> reviews) {
        return new ResponseWrapper<>(toSimpleResponse(reviews));
    }

    private static List<ReviewSimpleResponse> toSimpleResponse(List<Review> reviews) {
        return reviews.stream().map(ReviewSimpleResponse::new).toList();
    }
}
