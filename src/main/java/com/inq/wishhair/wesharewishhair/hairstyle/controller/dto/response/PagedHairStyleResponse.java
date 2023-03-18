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

    public static PagedHairStyleResponse of(List<HairStyleResponse> result) {
        return new PagedHairStyleResponse(result, result.size());
    }
}
