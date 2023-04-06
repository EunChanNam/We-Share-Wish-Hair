package com.inq.wishhair.wesharewishhair.review.service.dto.response;

import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ReviewResponseAssembler {

    public static PagedResponse<ReviewResponse> toPagedReviewResponse(Slice<Review> slice) {
        return new PagedResponse<>(transferContentToResponse(slice));
    }

    private static Slice<ReviewResponse> transferContentToResponse(Slice<Review> slice) {
        return slice.map(ReviewResponseAssembler::toReviewResponse);
    }

    private static ReviewResponse toReviewResponse(Review review) {
        return new ReviewResponse(review);
    }
}
