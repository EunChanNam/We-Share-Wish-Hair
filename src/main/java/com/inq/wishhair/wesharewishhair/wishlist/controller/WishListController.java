package com.inq.wishhair.wesharewishhair.wishlist.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.global.dto.response.Success;
import com.inq.wishhair.wesharewishhair.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WishListController {

    private final WishListService wishListService;

    @PostMapping("/wish_list/{hairStyleId}")
    public ResponseEntity<Success> createWishList(
            @PathVariable Long hairStyleId,
            @ExtractPayload Long userId) {

        Long wishListId = wishListService.createWishList(hairStyleId, userId);

        return ResponseEntity
                .created(URI.create("/api/wish_list/" + wishListId))
                .body(new Success());
    }

    @DeleteMapping("/wish_list/{id}")
    public ResponseEntity<Success> deleteWishList(@PathVariable Long id) {

        wishListService.deleteWishList(id);
        return ResponseEntity.ok(new Success());
    }
}
