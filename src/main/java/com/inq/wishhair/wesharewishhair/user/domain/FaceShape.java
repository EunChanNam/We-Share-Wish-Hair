package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class FaceShape {

    @Enumerated(EnumType.STRING)
    private Tag tag;

    public FaceShape(Tag tag) {
        validateTagType(tag);
        this.tag = tag;
    }

    private void validateTagType(Tag tag) {
        if (!tag.isFaceShapeType()) {
            throw new WishHairException(ErrorCode.USER_TAG_MISMATCH);
        }
    }
}
