package com.inq.wishhair.wesharewishhair.global.fixture;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponse;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HashTagResponse;
import com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponse;
import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum HairStyleFixture {

    A("hairStyleA", new ArrayList<>(List.of("one.png", "two.png")),
            new ArrayList<>(List.of(Tag.PERM, Tag.LONG, Tag.SQUARE, Tag.UPSTAGE)), Sex.WOMAN, 3),
    B("hairStyleB", new ArrayList<>(List.of("three.png", "four.png")),
            new ArrayList<>(List.of(Tag.PERM, Tag.SHORT, Tag.NEAT, Tag.OVAL)), Sex.MAN, 2),
    C("hairStyleC", new ArrayList<>(List.of("five.png", "six.png")),
            new ArrayList<>(List.of(Tag.PERM, Tag.SHORT, Tag.UPSTAGE, Tag.CUTE, Tag.OBLONG)), Sex.WOMAN, 4),
    D("hairStyleD", new ArrayList<>(List.of("seven.png", "eight.png")),
            new ArrayList<>(List.of(Tag.PERM, Tag.LONG, Tag.HARD, Tag.FORMAL, Tag.OBLONG)), Sex.WOMAN, 2),
    E("hairStyleE", new ArrayList<>(List.of("nine.png", "ten.png")),
            new ArrayList<>(List.of(Tag.PERM, Tag.SHORT, Tag.LIGHT, Tag.FORMAL, Tag.OBLONG)), Sex.WOMAN, 3);

    private final String name;

    private final List<String> filenames;

    private final List<Tag> tags;

    private final Sex sex;

    private final int wishListCount;

    public HairStyle toEntity() {
        List<Photo> photos = filenames.stream()
                .map((originalFilename) -> Photo.of(originalFilename, originalFilename))
                .toList();
        HairStyle hairStyle = HairStyle.createHairStyle(name, sex, photos, tags);
        for (int i = 0; i < wishListCount; i++) {
            hairStyle.plusWishListCount();
        }
        return hairStyle;
    }

    public HairStyleResponse toResponse(Long id) {
        HairStyle hairStyle = toEntity();
        List<PhotoResponse> photoResponses = generatePhotoResponses(hairStyle);
        List<HashTagResponse> hashTagResponses = generateHashTagResponses(hairStyle);
        return new HairStyleResponse(id, name, photoResponses, hashTagResponses);
    }

    private List<HashTagResponse> generateHashTagResponses(HairStyle hairStyle) {
        return hairStyle.getHashTags().stream()
                .map(HashTagResponse::new).toList();
    }

    private List<PhotoResponse> generatePhotoResponses(HairStyle hairStyle) {
        return hairStyle.getPhotos().stream()
                .map(PhotoResponse::new).toList();
    }
}
