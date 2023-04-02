package com.inq.wishhair.wesharewishhair.review.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewDeleteRequest {

    @NotNull
    private Long reviewId;
}
