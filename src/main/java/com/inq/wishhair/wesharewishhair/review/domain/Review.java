package com.inq.wishhair.wesharewishhair.review.domain;

import com.inq.wishhair.wesharewishhair.auditing.BaseEntity;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import com.inq.wishhair.wesharewishhair.review.domain.enums.Score;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private User writer;

    private Contents contents;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Score score;

    @OneToMany(mappedBy = "review",
            cascade = CascadeType.PERSIST) // 사진을 값타입 컬렉션 처럼 사용
    private final List<Photo> photos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hair_style_id")
    private HairStyle hairStyle;

    //==생성 메서드==//
    private Review(User writer, String contents, Score score, List<String> photos, HairStyle hairStyle) {
        this.writer = writer;
        this.contents = new Contents(contents);
        this.score = score;
        applyPhotos(photos);
        this.hairStyle = hairStyle;
        this.createdDate = LocalDateTime.now();
    }

    public static Review createReview(
            User user, String contents, Score score, List<String> photos, HairStyle hairStyle) {
        return new Review(user, contents, score, photos, hairStyle);
    }

    //편의 메서드
    public String getContentsValue() {
        return contents.getValue();
    }

    public boolean isWriter(Long userId) {
        return this.writer.getId().equals(userId);
    }

    private void applyPhotos(List<String> storeUrls) {
        storeUrls.forEach(storeUrl -> photos.add(Photo.createReviewPhoto(storeUrl, this)));
    }
}
