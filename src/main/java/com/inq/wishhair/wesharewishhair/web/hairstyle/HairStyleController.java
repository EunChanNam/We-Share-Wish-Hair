package com.inq.wishhair.wesharewishhair.web.hairstyle;

import com.inq.wishhair.wesharewishhair.domain.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.service.HairStyleService;
import com.inq.wishhair.wesharewishhair.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.domain.login.dto.UserSessionDto;
import com.inq.wishhair.wesharewishhair.common.consts.SessionConst;
import com.inq.wishhair.wesharewishhair.web.hairstyle.dto.response.HairStyleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HairStyleController {

    private final HairStyleService hairStyleService;

    @GetMapping("/hair_style/recommend")
    public ResponseEntity<List<HairStyleResponse>> respondRecommendedHairStyle(
            @PageableDefault(size = 3) Pageable pageable,
            @RequestParam List<Tag> tags,
            @SessionAttribute(SessionConst.LONGIN_MEMBER) UserSessionDto sessionDto) {

        List<HairStyle> hairStyles = hairStyleService.findRecommendedHairStyle(tags, sessionDto, pageable);

        List<HairStyleResponse> result = hairStyles.stream()
                .map(HairStyleResponse::new)
                .toList();

        return ResponseEntity.ok(result);
    }
}
