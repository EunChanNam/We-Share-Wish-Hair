package com.inq.wishhair.wesharewishhair.wishlist.service;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import com.inq.wishhair.wesharewishhair.wishlist.domain.WishList;
import com.inq.wishhair.wesharewishhair.wishlist.domain.WishListRepository;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.wishlist.service.dto.response.WishListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishListService {

    private final WishListRepository wishListRepository;
    private final HairStyleRepository hairStyleRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createWishList(Long hairStyleId, Long userId) {

        HairStyle hairStyle = findHairStyle(hairStyleId);
        User user = findUser(userId);

        WishList wishList = WishList.createWishList(user, hairStyle);

        return wishListRepository.save(wishList).getId();
    }

    @Transactional
    public void deleteWishList(Long wishListId) {

        wishListRepository.deleteById(wishListId);
    }

    public Slice<WishListResponse> getWishList(Long userId, Pageable pageable) {
        Slice<WishList> wishLists = wishListRepository.findByUser(userId, pageable);
        return transferContentToResponse(wishLists);
    }

    private Slice<WishListResponse> transferContentToResponse(Slice<WishList> wishLists) {
        return wishLists.map(wishList -> new WishListResponse(wishList.getHairStyle()));
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
    }

    private HairStyle findHairStyle(Long hairStyleId) {
        return hairStyleRepository.findById(hairStyleId)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
    }
}
