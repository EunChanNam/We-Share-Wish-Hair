package com.inq.wishhair.wesharewishhair.global.fixture;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.enums.Score;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public enum ReviewFixture {

    A("A contents", Score.S3, List.of("1.png")),
    B("B contents", Score.S4H, List.of("2.png", "3.png")),
    C("C contents", Score.S4H, new ArrayList<>()),
    D("D contents", Score.S4, List.of("4.png", "5.png")),
    E("E contents", Score.S3H, List.of("6.png", "7.png")),
    F("F contents", Score.S3, List.of("8.png", "9.png")),
    G("G contents", Score.S5, List.of("10.png")),
    ;


    private final String contents;
    private final Score score;
    private final List<String> originalFilenames;

    public Review toEntity(User user, HairStyle hairStyle) {
        List<Photo> photos = originalFilenames.stream().
                map(original -> Photo.of(original, original)).toList();
        return Review.createReview(user, contents, score, photos, hairStyle);
    }
}
