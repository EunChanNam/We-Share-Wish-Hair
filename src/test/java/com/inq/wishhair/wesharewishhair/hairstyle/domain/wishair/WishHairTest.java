package com.inq.wishhair.wesharewishhair.hairstyle.domain.wishair;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.WishHair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("HairStyle-WishHair - 도메인 테스트")
public class WishHairTest {

    @Test
    @DisplayName("WishHair 생성 메서드 테스트")
    void createWishHair() {
        //when
        WishHair wishHair = WishHair.createWishHair(1L, 1L);

        //then
        assertAll(
                () -> assertThat(wishHair.getHairStyleId()).isEqualTo(1L),
                () -> assertThat(wishHair.getUserId()).isEqualTo(1L)
        );
    }
}
