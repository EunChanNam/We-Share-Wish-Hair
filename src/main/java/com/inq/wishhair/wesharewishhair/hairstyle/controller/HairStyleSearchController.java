package com.inq.wishhair.wesharewishhair.hairstyle.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.dto.response.ResponseWrapper;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.service.HairStyleSearchService;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hair_style")
public class HairStyleSearchController {

    private final HairStyleSearchService hairStyleSearchService;

    @GetMapping("/recommend")
    public ResponseWrapper<HairStyleResponse> respondRecommendedHairStyle(
            @RequestParam(defaultValue = "ERROR") List<Tag> tags,
            @ExtractPayload Long userId) {

        validateHasTag(tags);

        return hairStyleSearchService.recommendHair(tags, userId);
    }

    @GetMapping("/home")
    public ResponseWrapper<HairStyleResponse> findHairStyleByFaceShape(
            @ExtractPayload Long userId) {

        return hairStyleSearchService.recommendHairByFaceShape(userId);
    }

    @GetMapping("/wish")
    public PagedResponse<HairStyleResponse> findWishHairStyles(@ExtractPayload Long useId,
                                                               Pageable pageable) {

        return hairStyleSearchService.findWishHairStyles(useId, pageable);
    }

    private void validateHasTag(List<Tag> tags) {
        if (tags.get(0).equals(Tag.ERROR)) {
            throw new WishHairException(ErrorCode.RUN_NOT_ENOUGH_TAG);
        }
    }
}
