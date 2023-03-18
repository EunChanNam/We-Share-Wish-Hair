package com.inq.wishhair.wesharewishhair.review.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PagedReviewResponse <T>{

    private T result;

    private int contentSize;
}
