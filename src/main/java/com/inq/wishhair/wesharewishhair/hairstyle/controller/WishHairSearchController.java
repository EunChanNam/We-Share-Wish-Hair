package com.inq.wishhair.wesharewishhair.hairstyle.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.hairstyle.service.WishHairSearchService;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.WishListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wish_list")
public class WishHairSearchController {

    private final WishHairSearchService wishHairSearchService;

    @GetMapping
    public ResponseEntity<PagedResponse<WishListResponse>> getWishList(
            @PageableDefault(size = 4) Pageable pageable,
            @ExtractPayload Long userId) {

        PagedResponse<WishListResponse> result = wishHairSearchService.findWishList(userId, pageable);

        return ResponseEntity.ok(result);
    }
}
