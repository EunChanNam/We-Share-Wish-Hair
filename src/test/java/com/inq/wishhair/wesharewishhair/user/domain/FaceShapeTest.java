package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FaceShapeTest {

    @Test
    @DisplayName("태그가 얼굴형 태그라면 생성한다")
    void test1() {
        //given
        Tag faceTag = Tag.ROUND;

        //when
        FaceShape faceShape = new FaceShape(faceTag);

        //then
        assertThat(faceShape.getTag()).isEqualTo(faceTag);
    }

    @Test
    @DisplayName("태그가 얼굴형 태그가 아니라면 예외가 발생한다")
    void test2() {
        //given
        Tag tag = Tag.COOL;
        ErrorCode expectedError = ErrorCode.USER_TAG_MISMATCH;

        //when, then
        assertThatThrownBy(() -> new FaceShape(tag))
                .isInstanceOf(WishHairException.class)
                .hasMessageContaining(expectedError.getMessage());
    }
}