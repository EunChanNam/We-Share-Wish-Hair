package com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HairStyleSimpleResponse {

    private Long hairStyleId;
    private String hairStyleName;

    public HairStyleSimpleResponse(HairStyle hairStyle) {
        this.hairStyleId = hairStyle.getId();
        this.hairStyleName = hairStyle.getName();
    }
}
