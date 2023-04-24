package com.inq.wishhair.wesharewishhair.photo.domain;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hair_style_id")
    private HairStyle hairStyle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(nullable = false, updatable = false, unique = true)
    private String storeUrl;

    //==생성 메서드==//
    private Photo(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public static Photo createReviewPhoto(String storeUrl, Review review) {
        Photo photo = new Photo(storeUrl);
        photo.review = review;
        return photo;
    }

    public static Photo createHairStylePhoto(String storeUrl, HairStyle hairStyle) {
        Photo photo = new Photo(storeUrl);
        photo.hairStyle = hairStyle;
        return photo;
    }
}
