package com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.HashTag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class HairStyleResponseAssembler {

    public static List<HairStyleResponse> toHairStyleResponses(List<HairStyle> hairStyles) {
        return hairStyles.stream()
                .map(HairStyleResponseAssembler::toHairStyleResponse)
                .toList();
    }

    private static HairStyleResponse toHairStyleResponse(HairStyle hairStyle) {
        return new HairStyleResponse(hairStyle);
    }

    public static List<HashTagResponse> toHashTagResponses(List<HashTag> hashTags) {
        return hashTags.stream().map(HashTagResponse::new).toList();
    }
}
