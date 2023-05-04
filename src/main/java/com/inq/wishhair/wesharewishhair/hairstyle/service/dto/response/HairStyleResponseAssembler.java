package com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response;

import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.dto.response.ResponseWrapper;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.HashTag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class HairStyleResponseAssembler {

    public static PagedResponse<HairStyleResponse> toPagedResponse(Slice<HairStyle> sliceResult) {
        return new PagedResponse<>(transferContentToResponse(sliceResult));
    }

    public static ResponseWrapper<HairStyleResponse> toWrappedHairStyleResponse(List<HairStyle> hairStyles) {
        return new ResponseWrapper<>(toHairStyleResponses(hairStyles));
    }

    public static ResponseWrapper<HairStyleSimpleResponse> toWrappedHairStyleSimpleResponse(List<HairStyle> hairStyles) {
        return new ResponseWrapper<>(toHairStyleSimpleResponses(hairStyles));
    }

    private static List<HairStyleSimpleResponse> toHairStyleSimpleResponses(List<HairStyle> hairStyles) {
        return hairStyles.stream().map(HairStyleSimpleResponse::new).toList();
    }

    private static Slice<HairStyleResponse> transferContentToResponse(Slice<HairStyle> slice) {
        return slice.map(HairStyleResponse::new);
    }

    private static List<HairStyleResponse> toHairStyleResponses(List<HairStyle> hairStyles) {
        return hairStyles.stream().map(HairStyleResponse::new).toList();
    }

    public static List<HashTagResponse> toHashTagResponses(List<HashTag> hashTags) {
        return hashTags.stream().map(HashTagResponse::new).toList();
    }
}
