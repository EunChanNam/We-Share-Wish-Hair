package com.inq.wishhair.wesharewishhair.hairstyle.service;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.WishHair;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.WishHairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishHairService {

    private final WishHairRepository wishHairRepository;
    private final HairStyleFindService hairStyleFindService;
    private final WishHairFindService wishHairFindService;

    @Transactional
    public void executeWish(Long hairStyleId, Long userId) {
        validateDoesWishHairExist(hairStyleId, userId);

        HairStyle hairStyle = hairStyleFindService.findById(hairStyleId);
        wishHairRepository.save(WishHair.createWishList(userId, hairStyle));
    }

    @Transactional
    public Long createWishList(Long hairStyleId, Long userId) {

        HairStyle hairStyle = hairStyleFindService.findWithLockById(hairStyleId);

        WishHair wishHair = WishHair.createWishList(userId, hairStyle);

        hairStyle.plusWishListCount();

        return wishHairRepository.save(wishHair).getId();
    }

    @Transactional
    public void deleteWishList(Long wishListId, Long userId) {
        WishHair wishHair = wishHairFindService.findByIdWithHairStyle(wishListId);
        validateIsHost(wishHair, userId);

        wishHair.getHairStyle().minusWishListCount();

        wishHairRepository.deleteById(wishListId);
    }

    private void validateDoesWishHairExist(Long hairStyleId, Long userId) {
        boolean exist = wishHairRepository.existByHairStyleIdAndUserId(hairStyleId, userId);
        if (!exist) {
            throw new WishHairException(ErrorCode.WISH_HAIR_NOT_EXIST);
        }
    }

    private void validateIsHost(WishHair wishHair, Long userId) {
        if (!wishHair.isHost(userId)) {
            throw new WishHairException(ErrorCode.WISH_LIST_NOT_HOST);
        }
    }
}
