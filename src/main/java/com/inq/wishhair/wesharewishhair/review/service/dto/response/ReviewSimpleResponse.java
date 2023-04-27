package com.inq.wishhair.wesharewishhair.review.service.dto.response;

import com.inq.wishhair.wesharewishhair.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewSimpleResponse {

    private Long reviewId;

    private String userNickname;

    private String hairStyleName;

    private String contents;

    public ReviewSimpleResponse(Review review) {
        this.reviewId = review.getId();
        this.userNickname = review.getWriter().getNicknameValue();
        this.hairStyleName = review.getHairStyle().getName();
        this.contents = review.getContentsValue();
    }
}
