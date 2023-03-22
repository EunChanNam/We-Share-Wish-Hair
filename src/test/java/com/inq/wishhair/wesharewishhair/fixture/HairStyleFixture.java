package com.inq.wishhair.wesharewishhair.fixture;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.HashTag;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponse;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HashTagResponse;
import com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponse;
import com.inq.wishhair.wesharewishhair.photo.entity.Photo;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum HairStyleFixture {

    A("hairStyleA", new ArrayList<>(List.of("test1.png", "test2.png", "test3.png", "test4.png")),
            new ArrayList<>(List.of(Tag.PERM, Tag.LONG, Tag.SQUARE, Tag.UPSTAGE)), Sex.WOMAN),
    B("hairStyleB", new ArrayList<>(List.of("test5.png", "test6.png", "test7.png", "test8.png")),
            new ArrayList<>(List.of(Tag.PERM, Tag.SHORT, Tag.NEAT, Tag.OVAL)), Sex.MAN),
    C("hairStyleC", new ArrayList<>(List.of("test9.png", "test10.png", "test11.png", "test12.png")),
            new ArrayList<>(List.of(Tag.PERM, Tag.SHORT, Tag.UPSTAGE, Tag.CUTE, Tag.OBLONG)), Sex.WOMAN),
    D("hairStyleD", new ArrayList<>(List.of("test13.png", "test14.png", "test15.png", "test16.png")),
            new ArrayList<>(List.of(Tag.PERM, Tag.LONG, Tag.HARD, Tag.FORMAL, Tag.OBLONG)), Sex.WOMAN),
    E("hairStyleE", new ArrayList<>(List.of("test13.png", "test14.png", "test15.png", "test16.png")),
            new ArrayList<>(List.of(Tag.PERM, Tag.SHORT, Tag.LIGHT, Tag.FORMAL, Tag.OBLONG)), Sex.WOMAN);

    private final String name;

    private final List<String> originalFilenames;

    private final List<Tag> tags;

    private final Sex sex;

    public HairStyle toEntity() {
        List<Photo> photos = originalFilenames.stream()
                .map((originalFilename) -> Photo.of(originalFilename, createStoreFilename(originalFilename)))
                .toList();
        List<HashTag> hashTags = tags.stream()
                .map(HashTag::of).toList();
        return HairStyle.createHairStyle(name, sex, photos, hashTags);
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

    private String createStoreFilename(String originalFilename) {
        String ext = getExt(originalFilename);
        return UUID.randomUUID() + ext;
    }

    private String getExt(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        return originalFilename.substring(index);
    }

}
