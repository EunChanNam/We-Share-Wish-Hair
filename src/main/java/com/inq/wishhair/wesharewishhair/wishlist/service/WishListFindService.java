package com.inq.wishhair.wesharewishhair.wishlist.service;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.wishlist.domain.WishList;
import com.inq.wishhair.wesharewishhair.wishlist.domain.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListFindService {

    private final WishListRepository wishListRepository;

    public WishList findByIdWithHairStyle(Long id) {
        return wishListRepository.findWithHairStyleById(id)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
    }
}
