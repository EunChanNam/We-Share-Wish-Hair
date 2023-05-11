package com.inq.wishhair.wesharewishhair.hairstyle.service;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.WishHair;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.WishHairRepository;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.WishHairResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishHairService {

    private final WishHairRepository wishHairRepository;

    @Transactional
    public void executeWish(Long hairStyleId, Long userId) {
        validateDoesNotExistWishHair(hairStyleId, userId);

        wishHairRepository.save(WishHair.createWishHair(userId, hairStyleId));
    }

    @Transactional
    public void cancelWish(Long hairStyleId, Long userId) {
         validateDoesWishHairExist(hairStyleId, userId);

         wishHairRepository.deleteByHairStyleIdAndUserId(hairStyleId, userId);
    }

    public WishHairResponse checkIsWishing(Long hairStyleId, Long userId) {
        return new WishHairResponse(existWishHair(hairStyleId, userId));
    }

    private boolean existWishHair(Long hairStyleId, Long userId) {
        return wishHairRepository.existsByHairStyleIdAndUserId(hairStyleId, userId);
    }

    private void validateDoesWishHairExist(Long hairStyleId, Long userId) {
        if (!existWishHair(hairStyleId, userId)) {
            throw new WishHairException(ErrorCode.WISH_HAIR_NOT_EXIST);
        }
    }

    private void validateDoesNotExistWishHair(Long hairStyleId, Long userId) {
        if (existWishHair(hairStyleId, userId)) {
            throw new WishHairException(ErrorCode.WISH_HAIR_ALREADY_EXIST);
        }
    }
}
