package com.inq.wishhair.wesharewishhair.hairstyle.domain.wishair;

import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.WishHair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HairStyle-WishHair - DataJpaTest")
public class WishHairRepositoryTest extends RepositoryTest {

    private final Long userId = 1L;
    private final Long hairStyleId = 1L;

    @Test
    @DisplayName("헤어스타일과 사용자의 아이디로 찜을 삭제한다")
    void deleteByHairStyleIdAndUserId() {
        //given
        wishHairRepository.save(WishHair.createWishHair(userId, hairStyleId));

        //when
        wishHairRepository.deleteByHairStyleIdAndUserId(hairStyleId, userId);

        //then
        List<WishHair> result = wishHairRepository.findAll();
        assertThat(result).isEmpty();
    }

    @Nested
    @DisplayName("찜 존재 유무 확인 쿼리")
    class existByHairStyleIdAndUserId {
        @Test
        @DisplayName("존재하여 true 를 반환한다")
        void exist() {
            //given
            wishHairRepository.save(WishHair.createWishHair(userId, hairStyleId));

            //when
            boolean result = wishHairRepository.existsByHairStyleIdAndUserId(hairStyleId, userId);

            //then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("존재하지 않아 false 를 반환한다")
        void notExist() {
            //when
            boolean result = wishHairRepository.existsByHairStyleIdAndUserId(hairStyleId, userId);

            //then
            assertThat(result).isFalse();
        }
    }

}
