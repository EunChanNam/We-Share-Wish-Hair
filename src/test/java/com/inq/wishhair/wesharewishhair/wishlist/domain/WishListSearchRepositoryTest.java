package com.inq.wishhair.wesharewishhair.wishlist.domain;

import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.utils.PageableUtils.getDefaultPageable;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("WishListSearchRepositoryTest - DataJpaTest")
public class WishListSearchRepositoryTest extends RepositoryTest {

    @Autowired
    private WishListSearchRepository wishListSearchRepository;

    private Long userId;
    private WishList[] wishLists;

    @BeforeEach
    void setUp() {
        //given
        userId = 1L;

        HairStyleFixture[] hairStyleFixtures = HairStyleFixture.values();
        wishLists = new WishList[hairStyleFixtures.length];

        for (int index = 0; index < hairStyleFixtures.length; index++) {
            wishLists[index] = WishList.createWishList(userId, hairStyleFixtures[index].toEntity());
        }
    }

    @Test
    @DisplayName("사용자 아이디로 찜목록을 최신 날짜 순으로 조회한다")
    void findByUser() {
        //given
        saveWishLists(List.of(0, 1, 2, 3), List.of(now(), now().minusMinutes(10), now().minusHours(3), now().plusHours(3)));

        //when
        Slice<WishList> result = wishListSearchRepository.findByUser(userId, getDefaultPageable());

        //then
        assertThat(result.hasNext()).isFalse();
        assertWishListsMatch(result.getContent(), List.of(3, 0, 1, 2));
    }

    private void saveWishLists(List<Integer> indexes, List<LocalDateTime> times) {
        for (int i = 0; i < indexes.size(); i++) {
            int index = indexes.get(i);
            LocalDateTime time = times.get(i);

            ReflectionTestUtils.setField(wishLists[index], "createdDate", time);

            hairStyleRepository.save(wishLists[index].getHairStyle());
            wishListSearchRepository.save(wishLists[index]);
        }
    }

    private void assertWishListsMatch(List<WishList> results, List<Integer> indexes) {
        assertThat(results).hasSize(indexes.size());

        for (int i = 0; i < results.size(); i++) {
            WishList actual = results.get(i);
            WishList expected = wishLists[indexes.get(i)];

            assertThat(actual).isEqualTo(expected);
        }
    }
}
