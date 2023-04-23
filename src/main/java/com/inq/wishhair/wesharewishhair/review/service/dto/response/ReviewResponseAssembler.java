package com.inq.wishhair.wesharewishhair.review.service.dto.response;

import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.dto.response.ResponseWrapper;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.infra.query.dto.response.ReviewQueryResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ReviewResponseAssembler {

    public static PagedResponse<ReviewResponse> toPagedReviewResponse(Slice<ReviewQueryResponse> slice, Long userId) {
        return new PagedResponse<>(transferContentToResponse(slice, userId));
    }

    private static Slice<ReviewResponse> transferContentToResponse(Slice<ReviewQueryResponse> slice, Long userId) {
        return slice.map(review -> toReviewResponse(review, userId));
    }

    public static ReviewResponse toReviewResponse(ReviewQueryResponse review, Long userId) {
        return new ReviewResponse(review, userId);
    }

    public static ResponseWrapper<ReviewSimpleResponse> toWrappedSimpleResponse(List<Review> reviews) {
        return new ResponseWrapper<>(toSimpleResponse(reviews));
    }

    private static List<ReviewSimpleResponse> toSimpleResponse(List<Review> reviews) {
        return reviews.stream().map(ReviewSimpleResponse::new).toList();
    }
}
