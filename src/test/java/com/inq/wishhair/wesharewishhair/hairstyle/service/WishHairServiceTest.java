package com.inq.wishhair.wesharewishhair.hairstyle.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.WishHair;
import org.junit.jupiter.api.Assertions;
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
}
