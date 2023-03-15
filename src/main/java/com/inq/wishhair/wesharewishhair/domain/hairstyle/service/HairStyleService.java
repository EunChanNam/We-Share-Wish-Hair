package com.inq.wishhair.wesharewishhair.domain.hairstyle.service;

import com.inq.wishhair.wesharewishhair.domain.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.repository.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.domain.login.dto.UserSessionDto;
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

    public List<HairStyle> findRecommendedHairStyle(
            List<Tag> tags, UserSessionDto sessionDto, Pageable pageable) {

        List<HairStyle> hairStyles = hairStyleRepository.findByHashTags(tags, sessionDto.getSex(), pageable);
        /*지연로딩 데이터 가져오는 부분*/
        if (!hairStyles.isEmpty()) {
            hairStyles.get(0).getPhotos().isEmpty();
        }

        return hairStyles;
    }
}
