package com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.HashTag;
import lombok.Getter;

import java.util.List;

@Getter
public class HashTagResponse {

    private final List<String> tags;

    public HashTagResponse(List<HashTag> hashTags) {
        tags = hashTags.stream()
                .map(hashTag -> hashTag.getTag().getDescription())
                .toList();
    }
}
