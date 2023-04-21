package com.inq.wishhair.wesharewishhair.wishlist.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.wishlist.domain.WishList;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("WishListServiceTest - SpringBootTest")
public class WishListServiceTest extends ServiceTest {

    private Long userId;
    private HairStyle hairStyle;

    @BeforeEach
    void setUp() {
        //given
        userId = 1L;
        hairStyle = hairStyleRepository.save(HairStyleFixture.A.toEntity());
    }

    @Autowired
    private WishListService wishListService;

    @Test
    @DisplayName("찜목록 생성 서비스 테스트")
    void createWishList() {
        //when
        Long result = wishListService.createWishList(hairStyle.getId(), userId);

        //then
        assertAll(
                () -> assertThat(wishListRepository.findById(result)).isPresent(),
                () -> {
                    WishList actual = wishListRepository.findById(result).orElseThrow();
                    assertThat(actual.getUserId()).isEqualTo(userId);
                    assertThat(actual.getHairStyle()).isEqualTo(hairStyle);
                }
        );
    }

    @Nested
    @DisplayName("찜목록 삭제 서비스 테스트")
    class deleteWishList {
        @Test
        @DisplayName("찜목록을 삭제한다")
        void success() {
            //given
            Long wishListId = wishListRepository.save(WishList.createWishList(userId, hairStyle)).getId();

            //when
            wishListService.deleteWishList(wishListId, userId);

            //then
            assertThat(wishListRepository.findAll()).isEmpty();
        }

        @Test
        @DisplayName("찜 목록 소유자가 아니여서 실패한다")
        void failByNotHost() {
            //given
            Long wishListId = wishListRepository.save(WishList.createWishList(userId, hairStyle)).getId();

            //when, then
            assertThatThrownBy(() -> wishListService.deleteWishList(wishListId, 99L))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.WISH_LIST_NOT_HOST.getMessage());
        }
    }
}
