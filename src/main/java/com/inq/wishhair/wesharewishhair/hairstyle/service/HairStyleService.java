package com.inq.wishhair.wesharewishhair.hairstyle.service;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.hashtag.enums.Tag;
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

    public List<HairStyle> findRecommendedHairStyle(
            List<Tag> tags, Long userId, Pageable pageable) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
        List<HairStyle> hairStyles = hairStyleRepository.findByHashTags(tags, user.getSex(), pageable);
        /*지연로딩 데이터 가져오는 부분*/
        if (!hairStyles.isEmpty()) {
            hairStyles.get(0).getPhotos().isEmpty();
        }

        return hairStyles;
    }
}
