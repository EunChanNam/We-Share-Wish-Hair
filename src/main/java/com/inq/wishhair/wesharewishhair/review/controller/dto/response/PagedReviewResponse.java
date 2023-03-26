package com.inq.wishhair.wesharewishhair.review.controller.dto.response;

import com.inq.wishhair.wesharewishhair.global.dto.response.Paging;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@AllArgsConstructor
public class PagedReviewResponse {

    private List<ReviewResponse> result;

    private Paging paging;

    public PagedReviewResponse(Slice<ReviewResponse> slice) {
        this.result = slice.getContent();
        this.paging = new Paging(slice.getContent().size(), slice.getNumber(), slice.hasNext());
    }
}
