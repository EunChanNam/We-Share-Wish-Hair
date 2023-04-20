package com.inq.wishhair.wesharewishhair.wishlist.domain;

import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.user.domain.User;
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
        User user = UserFixture.B.toEntity();
        HairStyle hairStyle = HairStyleFixture.A.toEntity();

        //when
        WishList wishList = WishList.createWishList(user, hairStyle);

        //then
        assertAll(
                () -> assertThat(wishList.getUser()).isEqualTo(user),
                () -> assertThat(wishList.getHairStyle()).isEqualTo(hairStyle)
        );
    }
}
