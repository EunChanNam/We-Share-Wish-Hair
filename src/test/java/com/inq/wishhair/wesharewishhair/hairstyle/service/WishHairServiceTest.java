package com.inq.wishhair.wesharewishhair.hairstyle.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.WishHair;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.WishHairResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("WishHairServiceTest - SpringBootTest")
public class WishHairServiceTest extends ServiceTest {

    @Autowired
    private WishHairService wishHairService;

    private final Long hairStyleId = 1L;
    private final Long userId = 1L;

    @Nested
    @DisplayName("찜을 한다")
    class executeWish {
        @Test
        @DisplayName("찜을 성공한다")
        void success() {
            //when, then
            assertDoesNotThrow(() -> wishHairService.executeWish(hairStyleId, userId));

            List<WishHair> all = wishHairRepository.findAll();
            assertThat(all).hasSize(1);
        }

        @Test
        @DisplayName("이미 찜을 해서 실패한다")
        void failByAlreadyExist() {
            //given
            wishHairService.executeWish(hairStyleId, userId);

            //when
            assertThatThrownBy(() -> wishHairService.executeWish(hairStyleId, userId))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.WISH_HAIR_ALREADY_EXIST.getMessage());
        }
    }

    @Nested
    @DisplayName("찜을 취소한다")
    class cancelWish {
        @Test
        @DisplayName("찜 취소를 성공한다")
        void success() {
            //given
            wishHairRepository.save(WishHair.createWishHair(userId, hairStyleId));

            //when, then
            assertDoesNotThrow(() -> wishHairService.cancelWish(hairStyleId, userId));

            List<WishHair> all = wishHairRepository.findAll();
            assertThat(all).isEmpty();
        }

        @Test
        @DisplayName("찜한 헤어스타일이 아니라서 실패한다")
        void failByNotExist() {
            //given
            ErrorCode expectedError = ErrorCode.WISH_HAIR_NOT_EXIST;

            //when
            assertThatThrownBy(() -> wishHairService.cancelWish(hairStyleId, userId))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(expectedError.getMessage());
        }
    }

    @Nested
    @DisplayName("찜 여부를 조회한다")
    class checkIsWishing {
        @Test
        @DisplayName("찜을 하고 있지 않아 false 를 반환한다")
        void isFalse() {
            //when
            WishHairResponse result = wishHairService.checkIsWishing(hairStyleId, userId);

            //then
            assertThat(result.isWishing()).isFalse();
        }

        @Test
        @DisplayName("찜을 하고 있어 true 를 반환한다")
        void isTrue() {
            //given
            wishHairRepository.save(WishHair.createWishHair(userId, hairStyleId));

            //when
            WishHairResponse result = wishHairService.checkIsWishing(hairStyleId, userId);

            //then
            assertThat(result.isWishing()).isTrue();
        }
    }
}
