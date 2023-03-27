package com.inq.wishhair.wesharewishhair.hairstyle.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.service.HairStyleService;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponse;
import com.inq.wishhair.wesharewishhair.hairstyle.controller.dto.response.PagedHairStyleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hair_style")
public class HairStyleController {

    private final HairStyleService hairStyleService;

    @GetMapping("/recommend")
    public ResponseEntity<PagedHairStyleResponse> respondRecommendedHairStyle(
            @PageableDefault(size = 4) Pageable pageable,
            @RequestParam(defaultValue = "ERROR") List<Tag> tags,
            @ExtractPayload Long userId) {

        validateHasTag(tags);

        List<HairStyleResponse> result = hairStyleService.findRecommendedHairStyle(tags, userId, pageable);

        return ResponseEntity.ok(toPagedResponse(result));
    }

    @GetMapping("/home")
    public ResponseEntity<PagedHairStyleResponse> findHairStyleByFaceShape(
            @PageableDefault(size = 4) Pageable pageable,
            @ExtractPayload Long userId) {

        List<HairStyleResponse> result = hairStyleService.findHairStyleByFaceShape(userId, pageable);

        return ResponseEntity.ok(toPagedResponse(result));
    }

    private void validateHasTag(List<Tag> tags) {
        if (tags.get(0).equals(Tag.ERROR)) {
            throw new WishHairException(ErrorCode.RUN_NOT_ENOUGH_TAG);
        }
    }

    private PagedHairStyleResponse toPagedResponse(List<HairStyleResponse> result) {
        return new PagedHairStyleResponse(result);
    }
}
