package com.inq.wishhair.wesharewishhair.hairstyle.service;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.HashTag;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponse;
import com.inq.wishhair.wesharewishhair.user.domain.FaceShape;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class HairStyleService {

    private final HairStyleRepository hairStyleRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<HairStyleResponse> findRecommendedHairStyle(
            List<Tag> tags, Long userId, Pageable pageable) {

        User user = findUser(userId);
        List<HairStyle> hairStyles = hairStyleRepository.findByHashTags(tags, user.getSex(), pageable);

        Tag faceShapeTag = extractFaceShapeTag(tags);
        filterHasFaceShapeTag(hairStyles, faceShapeTag);

        updateFaceShape(user, hairStyles);

        return generateHairStyleResponses(hairStyles);
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

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
    }

    private void updateFaceShape(User user, List<HairStyle> hairStyles) {
        if (!hairStyles.isEmpty()) {
            HairStyle firstHairStyle = hairStyles.get(0);
            Tag faceShapeTag = firstHairStyle.findFaceShapeTag();
            user.updateFaceShape(new FaceShape(faceShapeTag));
        }
    }

    private List<HairStyleResponse> generateHairStyleResponses(List<HairStyle> hairStyles) {
        return hairStyles.stream()
                .map(HairStyleResponse::new)
                .toList();
    }
}
