package com.inq.wishhair.wesharewishhair.review.domain;

import com.inq.wishhair.wesharewishhair.auditing.BaseEntity;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.LikeReview;
import com.inq.wishhair.wesharewishhair.photo.entity.Photo;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.LikeReviews;
import com.inq.wishhair.wesharewishhair.review.enums.Score;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Score score;

    @OneToMany(mappedBy = "review",
            cascade = CascadeType.ALL,
            orphanRemoval = true) // 사진을 값타입 컬렉션 처럼 사용
    private List<Photo> photos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hair_style_id")
    private HairStyle hairStyle;

    @Embedded
    private LikeReviews likeReviews;

    //==생성 메서드==//
    public static Review createReview(
            User user, String contents, Score score, List<Photo> photos, HairStyle hairStyle) {
        Review review = new Review();
        review.user = user;
        review.contents = contents;
        review.score = score;
        photos.forEach(photo -> {
            photo.registerReview(review);
            review.photos.add(photo);
        });
        review.hairStyle = hairStyle;
        return review;
    }

    //편의 메서드
    public void executeLike(User user) {
        likeReviews.executeLike(user, this);
    }
}
