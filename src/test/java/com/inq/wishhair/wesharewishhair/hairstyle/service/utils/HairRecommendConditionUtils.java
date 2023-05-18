package com.inq.wishhair.wesharewishhair.hairstyle.service.utils;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.utils.HairRecommendCondition;
import com.inq.wishhair.wesharewishhair.user.domain.User;

import java.util.List;

public abstract class HairRecommendConditionUtils {

    public static HairRecommendCondition mainRecommend(List<Tag> tags, User user) {
        return HairRecommendCondition.mainRecommend(tags, user.getFaceShapeTag(), user.getSex());
    }

    public static HairRecommendCondition subRecommend(User user) {
        return HairRecommendCondition.subRecommend(user.getFaceShape(), user.getSex());
    }
}
