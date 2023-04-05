package com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums;

public enum TagType {
    NORMAL, FACE_SHAPE;

    public boolean isFaceType() {
        return this.equals(FACE_SHAPE);
    }
}
