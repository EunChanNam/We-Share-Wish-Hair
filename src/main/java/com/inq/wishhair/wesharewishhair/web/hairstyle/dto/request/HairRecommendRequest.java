package com.inq.wishhair.wesharewishhair.web.hairstyle.dto.request;

import com.inq.wishhair.wesharewishhair.domain.hashtag.enums.Tag;
import lombok.Getter;

import java.util.List;

@Getter
public class HairRecommendRequest {

    private List<Tag> tags;
}
