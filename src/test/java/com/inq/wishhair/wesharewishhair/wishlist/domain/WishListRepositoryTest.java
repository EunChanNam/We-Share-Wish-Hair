package com.inq.wishhair.wesharewishhair.wishlist.domain;

import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("WishListRepositoryTest - DataJpaTest")
public class WishListRepositoryTest extends RepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HairStyleRepository hairStyleRepository;

    private WishList wishList;
    private User user;
    private HairStyle hairStyle;

    @BeforeEach
    void setUp() {
        //given
        user = userRepository.save(UserFixture.B.toEntity());
        hairStyle = hairStyleRepository.save(HairStyleFixture.A.toEntity());

        wishList = wishListRepository.save(WishList.createWishList(user, hairStyle));
    }

    @Test
    @DisplayName("찜항목을 아이디로 헤어스타일 정보와 함께 조회한다")
    void findWithHairStyleById() {
        //when
        Optional<WishList> result = wishListRepository.findById(wishList.getId());

        //then
        assertAll(
                () -> assertThat(result).isPresent(),
                () -> {
                    WishList actual = result.orElseThrow();
                    assertThat(actual.getHairStyle()).isEqualTo(hairStyle);
                    assertThat(actual.getUser()).isEqualTo(user);
                }
        );
    }
}
