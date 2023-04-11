package com.inq.wishhair.wesharewishhair.global.fixture;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.enums.Score;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public enum ReviewFixture {

    A("A contents", Score.S3, List.of("one.png")),
    B("B contents", Score.S4H, List.of("one.png", "two.png")),
    C("C contents", Score.S4H, new ArrayList<>()),
    D("D contents", Score.S4, List.of("one.png", "two.png")),
    E("E contents", Score.S3H, List.of("one.png", "two.png")),
    F("F contents", Score.S3, List.of("`one`.png", "two.png")),
    G("G contents", Score.S5, List.of("one.png", "two.png")),
    ;


    private final String contents;
    private final Score score;
    private final List<String> originalFilenames;

    public Review toEntity(User user, HairStyle hairStyle) {
        List<Photo> photos = originalFilenames.stream().
                map(original -> Photo.of(original, createStoreFilename(original))).toList();
        return Review.createReview(user, contents, score, photos, hairStyle);
    }

    private String createStoreFilename(String originalFilename) {
        String ext = getExt(originalFilename);
        return UUID.randomUUID() + ext;
    }

    private String getExt(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        return originalFilename.substring(index);
    }
}
