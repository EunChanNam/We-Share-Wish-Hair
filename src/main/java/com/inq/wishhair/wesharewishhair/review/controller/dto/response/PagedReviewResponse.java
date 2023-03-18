package com.inq.wishhair.wesharewishhair.review.controller.dto.response;

import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.wishlist.controller.dto.response.PagedWishListResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PagedReviewResponse {

    private List<ReviewResponse> result;

    private int contentSize;

    public static PagedReviewResponse of(List<ReviewResponse> result) {
        return new PagedReviewResponse(result, result.size());
    }
}
