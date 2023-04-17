package com.inq.wishhair.wesharewishhair.hairstyle.service;

import com.inq.wishhair.wesharewishhair.global.dto.response.ResponseWrapper;
import com.inq.wishhair.wesharewishhair.global.utils.PageableUtils;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleSearchRepository;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.HashTag;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponse;
import com.inq.wishhair.wesharewishhair.user.domain.FaceShape;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.utils.PageableUtils.*;
import static com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponseAssembler.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class HairStyleSearchService {

    private final HairStyleSearchRepository hairStyleSearchRepository;
    private final UserFindService userFindService;

    @Transactional
    public ResponseWrapper<HairStyleResponse> recommendHair(
            List<Tag> tags, Long userId) {
        User user = userFindService.findByUserId(userId);

        validateUserHasFaceShapeTag(user);

        Tag faceShapeTag = user.getFaceShape();
        tags.add(faceShapeTag);

        List<HairStyle> hairStyles = hairStyleSearchRepository.findByHashTags(tags, user.getSex(), getDefaultPageable());
        filterHasFaceShapeTag(hairStyles, faceShapeTag);

        return toWrappedHairStyleResponse(hairStyles);
    }

    public ResponseWrapper<HairStyleResponse> recommendHairByFaceShape(Long userId) {
        User user = userFindService.findByUserId(userId);
        Pageable pageable = generateSimplePageable(4);

        if (user.existFaceShape()) {
            List<HairStyle> hairStyles = hairStyleSearchRepository.findByFaceShapeTag(user.getFaceShape(), user.getSex(), pageable);
            return toWrappedHairStyleResponse(hairStyles);
        } else {
            List<HairStyle> hairStyles = hairStyleSearchRepository.findByNoFaceShapeTag(user.getSex(), pageable);
            return toWrappedHairStyleResponse(hairStyles);
        }
    }

    private void validateUserHasFaceShapeTag(User user) {
        if (!user.existFaceShape()) {
            throw new WishHairException(ErrorCode.USER_NO_FACE_SHAPE_TAG);
        }
    }

    private void filterHasFaceShapeTag(List<HairStyle> hairStyles, Tag faceShapeTag) {
        hairStyles.removeIf(hairStyle -> !hairStyle.getHashTags().stream()
                .map(HashTag::getTag)
                .toList()
                .contains(faceShapeTag));
    }
}
