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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishListService {

    private final WishListRepository wishListRepository;
    private final HairStyleRepository hairStyleRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createWishList(WishList wishList, Long hairStyleId, Long userId) {

        HairStyle hairStyle = hairStyleRepository.findById(hairStyleId)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
        wishList.registerHairStyle(hairStyle);
        wishList.registerUser(user);

        return wishListRepository.save(wishList).getId();
    }

    @Transactional
    public void deleteWishList(Long wishListId) {

        wishListRepository.deleteById(wishListId);
    }

    public List<WishListResponse> getWishList(Long userId, Pageable pageable) {
        List<WishList> wishLists = wishListRepository.findByUser(userId, pageable);
        return toResponse(wishLists);
    }

    private List<WishListResponse> toResponse(List<WishList> wishLists) {
        return wishLists.stream()
                .map(wishList -> new WishListResponse(wishList.getHairStyle()))
                .toList();
    }
}
