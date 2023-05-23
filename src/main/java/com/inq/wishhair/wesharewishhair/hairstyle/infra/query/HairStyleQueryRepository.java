package com.inq.wishhair.wesharewishhair.hairstyle.infra.query;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.utils.HairRecommendCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface HairStyleQueryRepository {

    List<HairStyle> findByRecommend(HairRecommendCondition condition, Pageable pageable);

    List<HairStyle> findByFaceShape(HairRecommendCondition condition, Pageable pageable);

    Slice<HairStyle> findByWish(Long userId, Pageable pageable);
}
