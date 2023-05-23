package com.inq.wishhair.wesharewishhair.hairstyle.service;

import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.dto.response.ResponseWrapper;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponse;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleSimpleResponse;
import com.inq.wishhair.wesharewishhair.hairstyle.utils.HairRecommendCondition;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.utils.PageableGenerator.*;
import static com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponseAssembler.*;
import static com.inq.wishhair.wesharewishhair.hairstyle.utils.HairRecommendCondition.mainRecommend;
import static com.inq.wishhair.wesharewishhair.hairstyle.utils.HairRecommendCondition.subRecommend;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class HairStyleSearchService {

    private final HairStyleRepository hairStyleRepository;
    private final UserFindService userFindService;

    //메인 추천 로직
    public ResponseWrapper<HairStyleResponse> recommendHair(List<Tag> tags, Long userId) {
        User user = userFindService.findByUserId(userId);

        validateUserHasFaceShapeTag(user);

        HairRecommendCondition condition = mainRecommend(tags, user.getFaceShapeTag(), user.getSex());
        List<HairStyle> hairStyles = hairStyleRepository.findByRecommend(condition, getDefaultPageable());

        return toWrappedHairStyleResponse(hairStyles);
    }

    //홈 화면 사용자 맞춤 추천 로직
    public ResponseWrapper<HairStyleResponse> recommendHairByFaceShape(Long userId) {
        User user = userFindService.findByUserId(userId);

        HairRecommendCondition condition = subRecommend(user.getFaceShape(), user.getSex());
        List<HairStyle> hairStyles = hairStyleRepository.findByFaceShape(condition, getDefaultPageable());
        return toWrappedHairStyleResponse(hairStyles);
    }

    //찜한 헤어스타일 조회 로직
    public PagedResponse<HairStyleResponse> findWishHairStyles(Long userId, Pageable pageable) {
        Slice<HairStyle> sliceResult = hairStyleRepository.findByWish(userId, pageable);
        return toPagedResponse(sliceResult);
    }

    //전체 헤어스타일 조회 로직
    public ResponseWrapper<HairStyleSimpleResponse> findAllHairStyle() {
        List<HairStyle> result = hairStyleRepository.findAllByOrderByName();
        return toWrappedHairStyleSimpleResponse(result);
    }

    private void validateUserHasFaceShapeTag(User user) {
        if (!user.existFaceShape()) {
            throw new WishHairException(ErrorCode.USER_NO_FACE_SHAPE_TAG);
        }
    }
}
