package com.inq.wishhair.wesharewishhair.wishlist.domain;

import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishlist.WishList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("WishList - 도매인 테스트")
public class WishListTest {

    @Test
    @DisplayName("WishList 를 생성한다")
    void createWishList() {
        //given
        Long userId = 1L;
        HairStyle hairStyle = HairStyleFixture.A.toEntity();

        //when
        WishList wishList = WishList.createWishList(userId, hairStyle);

        //then
        assertAll(
                () -> assertThat(wishList.getUserId()).isEqualTo(userId),
                () -> assertThat(wishList.getHairStyle()).isEqualTo(hairStyle)
        );
    }
}
