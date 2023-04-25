package com.inq.wishhair.wesharewishhair.wishlist.domain;

import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.WishHair;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.WishHairSearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;

import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.utils.PageableUtils.getDefaultPageable;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("WishListSearchRepositoryTest - DataJpaTest")
public class WishHairSearchRepositoryTest extends RepositoryTest {

    @Autowired
    private WishHairSearchRepository wishHairSearchRepository;

    private Long userId;
    private WishHair[] wishHairs;

    @BeforeEach
    void setUp() {
        //given
        userId = 1L;

        HairStyleFixture[] hairStyleFixtures = HairStyleFixture.values();
        wishHairs = new WishHair[hairStyleFixtures.length];

        for (int index = 0; index < hairStyleFixtures.length; index++) {
            wishHairs[index] = WishHair.createWishList(userId, hairStyleFixtures[index].toEntity());
        }
    }

    @Test
    @DisplayName("사용자 아이디로 찜목록을 최신 날짜 순으로 조회한다")
    void findByUser() {
        //given
        saveWishLists(List.of(0, 1, 2, 3));

        //when
        Slice<WishHair> result = wishHairSearchRepository.findByUser(userId, getDefaultPageable());

        //then
        assertThat(result.hasNext()).isFalse();
        assertWishListsMatch(result.getContent(), List.of(3, 2, 1, 0));
    }

    private void saveWishLists(List<Integer> indexes) {
        for (int index : indexes) {
            hairStyleRepository.save(wishHairs[index].getHairStyle());
            wishHairSearchRepository.save(wishHairs[index]);
        }
    }

    private void assertWishListsMatch(List<WishHair> results, List<Integer> indexes) {
        assertThat(results).hasSize(indexes.size());

        for (int i = 0; i < results.size(); i++) {
            WishHair actual = results.get(i);
            WishHair expected = wishHairs[indexes.get(i)];

            assertThat(actual).isEqualTo(expected);
        }
    }
}
