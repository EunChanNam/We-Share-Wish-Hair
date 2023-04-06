package com.inq.wishhair.wesharewishhair.hairstyle.service;

import com.inq.wishhair.wesharewishhair.global.dto.response.ResponseWrapper;
import com.inq.wishhair.wesharewishhair.global.utils.PageableUtils;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleSearchRepository;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.HashTag;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponse;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponseAssembler;
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

import static com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponseAssembler.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class HairStyleSearchService {

    private final HairStyleSearchRepository hairStyleSearchRepository;
    private final UserFindService userFindService;

    @Transactional
    public ResponseWrapper<List<HairStyleResponse>> findRecommendedHairStyle(
            List<Tag> tags, Long userId, Pageable pageable) {

        User user = userFindService.findByUserId(userId);
        List<HairStyle> hairStyles = hairStyleSearchRepository.findByHashTags(tags, user.getSex(), pageable);

        Tag faceShapeTag = extractFaceShapeTag(tags);
        filterHasFaceShapeTag(hairStyles, faceShapeTag);

        updateFaceShape(user, hairStyles);

        return ResponseWrapper.wrapResponse(toHairStyleResponses(hairStyles));
    }

    public ResponseWrapper<List<HairStyleResponse>> findHairStyleByFaceShape(Long userId) {
        User user = userFindService.findByUserId(userId);
        Pageable pageable = PageableUtils.generateSimplePageable(4);

        if (user.existFaceShape()) {
            List<HairStyle> hairStyles = hairStyleSearchRepository.findByFaceShapeTag(user.getFaceShape(), user.getSex(), pageable);
            return ResponseWrapper.wrapResponse(toHairStyleResponses(hairStyles));
        } else {
            List<HairStyle> hairStyles = hairStyleSearchRepository.findByNoFaceShapeTag(user.getSex(), pageable);
            return ResponseWrapper.wrapResponse(toHairStyleResponses(hairStyles));
        }
    }

    private void filterHasFaceShapeTag(List<HairStyle> hairStyles, Tag faceShapeTag) {
        hairStyles.removeIf(hairStyle -> !hairStyle.getHashTags().stream()
                .map(HashTag::getTag)
                .toList()
                .contains(faceShapeTag));
    }

    private Tag extractFaceShapeTag(List<Tag> tags) {
        return tags.stream()
                .filter(Tag::isFaceShapeType)
                .findAny().
                orElseThrow(() -> new WishHairException(ErrorCode.RUN_NO_FACE_SHAPE_TAG));
    }

    private void updateFaceShape(User user, List<HairStyle> hairStyles) {
        if (!hairStyles.isEmpty()) {
            HairStyle firstHairStyle = hairStyles.get(0);
            Tag faceShapeTag = firstHairStyle.findFaceShapeTag();
            user.updateFaceShape(new FaceShape(faceShapeTag));
        }
    }
}
