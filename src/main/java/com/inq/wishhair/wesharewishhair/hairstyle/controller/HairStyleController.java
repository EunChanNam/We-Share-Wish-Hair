package com.inq.wishhair.wesharewishhair.hairstyle.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.service.HairStyleService;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.controller.dto.response.HairStyleResponse;
import com.inq.wishhair.wesharewishhair.hairstyle.controller.dto.response.PagedHairStyleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HairStyleController {

    private final HairStyleService hairStyleService;

    @GetMapping("/hair_style/recommend")
    public ResponseEntity<PagedHairStyleResponse<List<HairStyleResponse>>> respondRecommendedHairStyle(
            @PageableDefault(size = 3) Pageable pageable,
            @RequestParam List<Tag> tags,
            @ExtractPayload Long userId) {

        List<HairStyle> hairStyles = hairStyleService.findRecommendedHairStyle(tags, userId, pageable);

        List<HairStyleResponse> result = toHairResponse(hairStyles);

        return ResponseEntity.ok(toPagedResponse(result, result.size()));
    }

    private static List<HairStyleResponse> toHairResponse(List<HairStyle> hairStyles) {
        return hairStyles.stream()
                .map(HairStyleResponse::new)
                .toList();
    }

    private <T> PagedHairStyleResponse<T> toPagedResponse(T result, int size) {
        return new PagedHairStyleResponse<>(result, size);
    }
}
