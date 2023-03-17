package com.inq.wishhair.wesharewishhair.web.wishlist;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.wishlist.WishList;
import com.inq.wishhair.wesharewishhair.wishlist.service.WishListService;
import com.inq.wishhair.wesharewishhair.web.wishlist.dto.response.WishListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WishListController {

    private final WishListService wishListService;

    @PostMapping("/wish_list/{hairStyleId}")
    public ResponseEntity<Void> createWishList(
            @PathVariable Long hairStyleId,
            @ExtractPayload Long userId) {

        WishList wishList = WishList.builder()
                .build();
        Long wishListId = wishListService.createWishList(wishList, hairStyleId, userId);

        return ResponseEntity
                .created(URI.create("/api/wish_list/" + wishListId))
                .build();
    }

    @DeleteMapping("wish_list/{id}")
    public ResponseEntity<Void> deleteWishList(@PathVariable Long id) {

        wishListService.deleteWishList(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/wish_list")
    public ResponseEntity<List<WishListResponse>> getWishList(
            @ExtractPayload Long userId) {

        List<WishList> wishLists = wishListService.getWishList(userId);
        List<WishListResponse> result = wishLists.stream()
                .map(wishList -> new WishListResponse(wishList.getHairStyle()))
                .toList();

        return ResponseEntity.ok(result);
    }

}
