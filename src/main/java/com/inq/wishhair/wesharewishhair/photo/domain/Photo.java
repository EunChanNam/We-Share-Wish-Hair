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

    @Column(nullable = false, updatable = false)
    private String originalFilename;

    @Column(nullable = false, updatable = false, unique = true)
    private String storeUrl;

    //==생성 메서드==//
    private Photo(String originalFilename, String storeUrl) {
        this.originalFilename = originalFilename;
        this.storeUrl = storeUrl;
    }

    public static Photo of(String originalFilename, String storeUrl) {
        return new Photo(originalFilename, storeUrl);
    }

    //==편의 메서드==//
    public void registerReview(Review review) {
        this.review = review;
    }

    public void registerHairStyle(HairStyle hairStyle) {
        this.hairStyle = hairStyle;
    }
}
