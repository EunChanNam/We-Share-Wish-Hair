package com.inq.wishhair.wesharewishhair.wishlist.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishlist.WishList;
import com.inq.wishhair.wesharewishhair.hairstyle.service.WishListFindService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("WishListFindServiceTest - SpringBootTest")
public class WishListFindServiceTest extends ServiceTest {

    @Autowired
    private WishListFindService wishListFindService;

    @Test
    @DisplayName("아이디로 찜 목록을 헤어스타일과 함께 조회한다")
    void findByIdWithHairStyle() {
        //given
        Long userId = 1L;
        HairStyle hairStyle = hairStyleRepository.save(HairStyleFixture.A.toEntity());
        WishList wishList = wishListRepository.save(WishList.createWishList(userId, hairStyle));

        //when
        WishList result = wishListFindService.findByIdWithHairStyle(wishList.getId());

        //then
        assertThat(result).isEqualTo(wishList);
    }
}
