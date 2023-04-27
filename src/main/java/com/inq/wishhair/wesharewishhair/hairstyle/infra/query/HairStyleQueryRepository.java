package com.inq.wishhair.wesharewishhair.hairstyle.infra.query;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.infra.query.dto.response.HairStyleQueryResponse;
import com.inq.wishhair.wesharewishhair.hairstyle.utils.HairRecommendCondition;
import com.inq.wishhair.wesharewishhair.user.domain.FaceShape;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface HairStyleQueryRepository {

    List<HairStyleQueryResponse> findByRecommend(HairRecommendCondition condition, Pageable pageable);

    Slice<HairStyle> findByWish(Long userId, Pageable pageable);
}
