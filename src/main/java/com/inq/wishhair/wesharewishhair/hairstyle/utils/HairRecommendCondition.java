package com.inq.wishhair.wesharewishhair.hairstyle.utils;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.Tag;
import com.inq.wishhair.wesharewishhair.user.domain.FaceShape;
import com.inq.wishhair.wesharewishhair.user.domain.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class HairRecommendCondition {

    private List<Tag> tags;
    private Tag userFaceShape;
    private Sex sex;

    public static HairRecommendCondition mainRecommend(List<Tag> tags, Tag userFaceShape, Sex sex) {
        return new HairRecommendCondition(tags, userFaceShape, sex);
    }

    public static HairRecommendCondition subRecommend(FaceShape faceShape, Sex sex) {
        if (faceShape == null || faceShape.getTag() == null) {
            return new HairRecommendCondition(null, null, sex);
        }
        return new HairRecommendCondition(null, faceShape.getTag(), sex);
    }
}
