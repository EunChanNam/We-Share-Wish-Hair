package com.inq.wishhair.wesharewishhair.review.infra.query.dto.response;

import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;


@Getter
public class ReviewQueryResponse {

    private final Review review;
    private final long likes;

    @QueryProjection
    public ReviewQueryResponse(Review review, long likes, Long likesId) {
        this.review = review;
        if (likesId == null) {
            this.likes = 0;
        } else this.likes = likes;
    }
}
