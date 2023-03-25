package com.inq.wishhair.wesharewishhair.user.controller.dto.response;

import com.inq.wishhair.wesharewishhair.global.dto.response.Paging;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.PointResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@AllArgsConstructor
public class PagedPointResponse {

    private List<PointResponse> result;

    private Paging paging;

    public PagedPointResponse(Slice<PointResponse> slice) {
        this.result = slice.getContent();
        this.paging = new Paging(slice.getSize(), slice.getNumber(), slice.hasNext());
    }
}
