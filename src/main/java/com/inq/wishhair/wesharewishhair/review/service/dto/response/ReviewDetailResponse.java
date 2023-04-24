package com.inq.wishhair.wesharewishhair.review.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewDetailResponse {

    private ReviewResponse reviewResponse;
    private boolean isLiking;
}
