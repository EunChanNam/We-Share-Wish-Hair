package com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.HashTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HashTagResponse {

    private final String tag;

    public HashTagResponse(HashTag hashTag) {
        tag = hashTag.getDescription();
    }
}
