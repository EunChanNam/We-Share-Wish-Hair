package com.inq.wishhair.wesharewishhair.hairstyle.controller.dto.response;

import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PagedHairStyleResponse {

    private List<HairStyleResponse> result;

    private int contentSize;

    public PagedHairStyleResponse(List<HairStyleResponse> result) {
        this.result = result;
        this.contentSize = result.size();
    }
}
