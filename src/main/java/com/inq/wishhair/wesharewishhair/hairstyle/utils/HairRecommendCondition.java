package com.inq.wishhair.wesharewishhair.hairstyle.utils;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class HairRecommendCondition() {

    private List<Tag> tags;
    private Tag userFaceShape;
    private Sex sex;

    public HairRecommendCondition(Tag userFaceShape, Sex sex) {
        this.userFaceShape = userFaceShape;
        this.sex = sex;
    }
}
