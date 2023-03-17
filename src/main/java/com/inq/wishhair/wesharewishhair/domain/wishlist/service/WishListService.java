package com.inq.wishhair.wesharewishhair.domain.wishlist.service;

import com.inq.wishhair.wesharewishhair.domain.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.repository.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.domain.user.repository.UserRepository;
import com.inq.wishhair.wesharewishhair.domain.wishlist.WishList;
import com.inq.wishhair.wesharewishhair.domain.wishlist.repository.WishListRepository;
import com.inq.wishhair.wesharewishhair.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.exception.WishHairException;
import lombok.RequiredArgsConstructor;
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

    public List<WishList> getWishList(Long userId) {
        return wishListRepository.findByUser(userId);
    }
}
