package com.inq.wishhair.wesharewishhair.wishlist.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponse;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.wishlist.domain.WishList;
import com.inq.wishhair.wesharewishhair.wishlist.service.dto.response.WishListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.utils.PageableUtils.getDefaultPageable;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("WishListSearchServiceTest - SpringBootTest")
public class WishListSearchServiceTest extends ServiceTest {

    @Autowired
    private WishListSearchService wishListSearchService;

    private User user;
    private WishList[] wishLists;

    @BeforeEach
    void setUp() {
        //given
        user = userRepository.save(UserFixture.B.toEntity());

        HairStyleFixture[] hairStyleFixtures = HairStyleFixture.values();
        wishLists = new WishList[hairStyleFixtures.length];

        for (int index = 0; index < hairStyleFixtures.length; index++) {
            wishLists[index] = WishList.createWishList(user, hairStyleFixtures[index].toEntity());
        }
    }

    @Test
    @DisplayName("사용자의 찜 목록을 최신 날짜 순으로 조회한다")
    void findWishList() {
        //given
        saveWishLists(List.of(0, 1, 2, 3), List.of(now(), now().minusMonths(1), now().minusMinutes(10), now().plusHours(3)));

        //when
        PagedResponse<WishListResponse> result = wishListSearchService.findWishList(user.getId(), getDefaultPageable());

        //then
        assertThat(result.getPaging().hasNext()).isFalse();
        assertResponsesMatch(result.getResult(), List.of(3, 0, 2, 1));
    }

    private void saveWishLists(List<Integer> indexes, List<LocalDateTime> times) {
        for (int i = 0; i < indexes.size(); i++) {
            int index = indexes.get(i);
            LocalDateTime time = times.get(i);

            ReflectionTestUtils.setField(wishLists[index], "createdDate", time);

            hairStyleRepository.save(wishLists[index].getHairStyle());
            wishListRepository.save(wishLists[index]);
        }
    }

    private void assertResponsesMatch(List<WishListResponse> responses, List<Integer> indexes) {
        assertThat(responses).hasSize(indexes.size());

        for (int i = 0; i < responses.size(); i++) {
            WishListResponse actual = responses.get(i);
            WishList expected = wishLists[indexes.get(i)];

            assertAll(
                    () -> assertThat(actual.getHairStyleId()).isEqualTo(expected.getHairStyle().getId()),
                    () -> assertThat(actual.getHairStyleName()).isEqualTo(expected.getHairStyle().getName()),
                    () -> {
                        List<String> actualPhotos = actual.getPhotos().stream().map(PhotoResponse::getStoreUrl).toList();
                        List<String> expectedPhotos = expected.getHairStyle().getPhotos().stream()
                                .map(Photo::getStoreUrl).toList();
                        assertThat(actualPhotos).containsAll(expectedPhotos);
                    }
            );
        }
    }
}
