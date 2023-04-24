package com.inq.wishhair.wesharewishhair.hairstyle.domain;


import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.HashTag;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HairStyle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String name;

    @OneToMany(mappedBy = "hairStyle", cascade = CascadeType.PERSIST)
    private final List<Photo> photos = new ArrayList<>();

    @OneToMany(mappedBy = "hairStyle", cascade = CascadeType.PERSIST)
    private final List<HashTag> hashTags = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Embedded
    private WishListCount wishListCount;

    //==생성 메서드==//
    private HairStyle(String name, Sex sex, List<String> photos, List<Tag> tags) {
        this.name = name;
        this.sex = sex;
        applyPhotos(photos);
        applyHasTags(tags);
        wishListCount = new WishListCount();
    }

    public static HairStyle createHairStyle(
            String name, Sex sex, List<String> photos, List<Tag> tags) {
        return new HairStyle(name, sex, photos, tags);
    }

    //==편의 메서드--//
    public void plusWishListCount() {
        wishListCount.plusWishListCount();
    }

    public void minusWishListCount() {
        wishListCount.minusWishListCount();
    }

    public int getWishListCount() {
        return wishListCount.getValue();
    }

    private void applyHasTags(List<Tag> tags) {
        tags.stream().map(HashTag::of).toList()
                .forEach(hashTag -> {
                    hashTag.registerHairStyle(this);
                    hashTags.add(hashTag);
                });
    }

    private void applyPhotos(List<String> storeUrls) {
        storeUrls.forEach(storeUrl -> photos.add(Photo.createHairStylePhoto(storeUrl, this)));
    }
}
