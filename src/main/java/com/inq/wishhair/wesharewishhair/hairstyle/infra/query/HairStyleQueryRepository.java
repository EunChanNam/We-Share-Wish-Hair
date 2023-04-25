package com.inq.wishhair.wesharewishhair.hairstyle.infra.query;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.user.domain.FaceShape;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface HairStyleQueryRepository {

    List<HairStyle> findByHashTags(List<Tag> tags, Sex sex, Pageable pageable);

    List<HairStyle> findByFaceShapeTag(FaceShape faceShape, Sex sex, Pageable pageable);

    Slice<HairStyle> findByWish(Long userId, Pageable pageable);
}
