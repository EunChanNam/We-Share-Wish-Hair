package com.inq.wishhair.wesharewishhair.wishlist.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.service.WishListSearchService;
import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponse;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishlist.WishList;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.WishListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.utils.PageableUtils.getDefaultPageable;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("WishListSearchServiceTest - SpringBootTest")
public class WishListSearchServiceTest extends ServiceTest {

    @Autowired
    private WishListSearchService wishListSearchService;

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
    @DisplayName("사용자의 찜 목록을 최신 날짜 순으로 조회한다")
    void findWishList() {
        //given
        saveWishLists(List.of(2, 1, 3, 0));

        //when
        PagedResponse<WishListResponse> result = wishListSearchService.findWishList(userId, getDefaultPageable());

        //then
        assertThat(result.getPaging().hasNext()).isFalse();
        assertResponsesMatch(result.getResult(), List.of(0, 3, 1, 2));
    }

    private void saveWishLists(List<Integer> indexes) {
        for (int index : indexes) {
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
