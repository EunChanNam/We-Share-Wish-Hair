package com.inq.wishhair.wesharewishhair.domain.hairstyle.service;

import com.inq.wishhair.wesharewishhair.domain.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.repository.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.domain.login.dto.UserSessionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class HairStyleService {

    private final HairStyleRepository hairStyleRepository;

    public List<HairStyle> findRecommendedHairStyle(List<Tag> tags, UserSessionDto sessionDto) {

        List<HairStyle> hairStyles = hairStyleRepository.findByHashTags(tags, tags.size(), sessionDto.getSex());
        /*지연로딩 데이터 가져오는 부분*/
        for (HairStyle hairStyle : hairStyles) {
            if (hairStyle.getPhotos().isEmpty()) {
                hairStyle.notHasPhoto();
            }
        }

        return hairStyles;
    }
}
