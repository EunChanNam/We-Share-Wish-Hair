package com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag;

public enum TagType {
    NORMAL, FACE_SHAPE;

    public boolean isFaceType() {
        return this.equals(FACE_SHAPE);
    }
}
