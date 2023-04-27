package com.inq.wishhair.wesharewishhair.hairstyle.infra.query.dto.response;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class HairStyleQueryResponse {

    private final HairStyle hairStyle;
    private final long wishCount;

    @QueryProjection
    public HairStyleQueryResponse(HairStyle hairStyle, long wishCount) {
        this.hairStyle = hairStyle;
        this.wishCount = wishCount;
    }
}
