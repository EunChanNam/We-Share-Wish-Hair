package com.inq.wishhair.wesharewishhair.wishlist.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.wishlist.domain.WishList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("WishListServiceTest - SpringBootTest")
public class WishListServiceTest extends ServiceTest {

    private User user;
    private HairStyle hairStyle;

    @BeforeEach
    void setUp() {
        //given
        user = userRepository.save(UserFixture.B.toEntity());
        hairStyle = hairStyleRepository.save(HairStyleFixture.A.toEntity());
    }

    @Autowired
    private WishListService wishListService;

    @Test
    @DisplayName("찜목록 생성 서비스 테스트")
    void createWishList() {
        //when
        Long result = wishListService.createWishList(hairStyle.getId(), user.getId());

        //then
        assertAll(
                () -> assertThat(wishListRepository.findById(result)).isPresent(),
                () -> {
                    WishList actual = wishListRepository.findById(result).orElseThrow();
                    assertThat(actual.getUser()).isEqualTo(user);
                    assertThat(actual.getHairStyle()).isEqualTo(hairStyle);
                }
        );
    }
}
