package com.inq.wishhair.wesharewishhair.review.domain;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Contents {

    private static final int MAX_LENGTH = 100;
    private static final int MIN_LENGTH = 5;

    @Column(name = "contents", nullable = false)
    private String value;

    public Contents(String value) {
        validateContentsLength(value);
        this.value = value;
    }

    private void validateContentsLength(String value) {
        String trimValue = value.replaceAll(" ", "");

        if (trimValue.length() > MAX_LENGTH || trimValue.length() < MIN_LENGTH) {
            throw new WishHairException(ErrorCode.CONTENTS_INVALID_LENGTH);
        }
    }
}
