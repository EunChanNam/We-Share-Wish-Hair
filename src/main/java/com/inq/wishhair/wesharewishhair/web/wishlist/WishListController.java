package com.inq.wishhair.wesharewishhair.web.wishlist;

import com.inq.wishhair.wesharewishhair.common.consts.SessionConst;
import com.inq.wishhair.wesharewishhair.domain.login.dto.UserSessionDto;
import com.inq.wishhair.wesharewishhair.domain.wishlist.WishList;
import com.inq.wishhair.wesharewishhair.domain.wishlist.service.WishListService;
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
    public ResponseEntity<Void> createWishList(
            @PathVariable Long hairStyleId,
            @SessionAttribute(SessionConst.LONGIN_MEMBER) UserSessionDto sessionDto) {

        WishList wishList = WishList.builder()
                .build();
        Long wishListId = wishListService.createWishList(wishList, hairStyleId, sessionDto.getId());

        return ResponseEntity
                .created(URI.create("/api/wish_list/" + wishListId))
                .build();
    }
}
