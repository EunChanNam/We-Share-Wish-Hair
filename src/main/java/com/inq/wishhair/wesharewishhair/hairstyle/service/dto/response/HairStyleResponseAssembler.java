package com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;

import java.util.List;

public interface HairStyleResponseAssembler {

    static List<HairStyleResponse> hairStyleResponses(List<HairStyle> hairStyles) {
        return hairStyles.stream()
                .map(HairStyleResponseAssembler::hairStyleResponse)
                .toList();
    }

    static HairStyleResponse hairStyleResponse(HairStyle hairStyle) {
        return new HairStyleResponse(hairStyle);
    }
}
