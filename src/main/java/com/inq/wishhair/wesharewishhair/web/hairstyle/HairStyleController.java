package com.inq.wishhair.wesharewishhair.web.hairstyle;

import com.inq.wishhair.wesharewishhair.domain.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.service.HairStyleService;
import com.inq.wishhair.wesharewishhair.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.web.hairstyle.dto.response.HairStyleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HairStyleController {

    private final HairStyleService hairStyleService;

    @GetMapping("/hair_style/recommend")
    public ResponseEntity<List<HairStyleResponse>> respondRecommendedHairStyle(
            @RequestParam List<Tag> tags) {

        List<HairStyle> hairStyles = hairStyleService.findRecommendedHairStyle(tags);

        List<HairStyleResponse> result = hairStyles.stream()
                .map(HairStyleResponse::new)
                .toList();

        return ResponseEntity.ok(result);
    }
}
