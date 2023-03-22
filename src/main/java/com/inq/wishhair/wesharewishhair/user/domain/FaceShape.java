package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class FaceShape {

    private Tag tag;

    public FaceShape(Tag tag) {
        this.tag = tag;
    }

    private void validateTagType() {
        if (!tag.isFaceShapeType()) {
            throw new WishHairException(ErrorCode.USER_INCONSISTENT_FACE_SHAPE);
        }
    }
}
