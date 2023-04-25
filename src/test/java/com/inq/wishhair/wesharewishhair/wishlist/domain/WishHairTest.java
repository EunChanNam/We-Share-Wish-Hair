package com.inq.wishhair.wesharewishhair.wishlist.domain;

import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.WishHair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("WishList - 도매인 테스트")
public class WishHairTest {

    @Test
    @DisplayName("WishList 를 생성한다")
    void createWishList() {
        //given
        Long userId = 1L;
        HairStyle hairStyle = HairStyleFixture.A.toEntity();

        //when
        WishHair wishHair = WishHair.createWishList(userId, hairStyle);

        //then
        assertAll(
                () -> assertThat(wishHair.getUserId()).isEqualTo(userId),
                () -> assertThat(wishHair.getHairStyle()).isEqualTo(hairStyle)
        );
    }
}
