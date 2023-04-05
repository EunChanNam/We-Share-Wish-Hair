package com.inq.wishhair.wesharewishhair.wishlist.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.wishlist.controller.dto.response.PagedWishListResponse;
import com.inq.wishhair.wesharewishhair.wishlist.service.WishListSearchService;
import com.inq.wishhair.wesharewishhair.wishlist.service.dto.response.WishListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wish_list")
public class WishListSearchController {

    private final WishListSearchService wishListSearchService;

    @GetMapping("/wish_list")
    public ResponseEntity<PagedWishListResponse> getWishList(
            @PageableDefault(size = 4) Pageable pageable,
            @ExtractPayload Long userId) {

        Slice<WishListResponse> result = wishListSearchService.findWishList(userId, pageable);

        return ResponseEntity.ok(new PagedWishListResponse(result));
    }
}
