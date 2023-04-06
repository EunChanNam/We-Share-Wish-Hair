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

    private Slice<ReviewResponse> transferContentToResponse(Slice<Review> slice) {
        return slice.map(this::toReviewResponse);
    }

    private ReviewResponse toReviewResponse(Review review) {
        return new ReviewResponse(review);
    }
}
