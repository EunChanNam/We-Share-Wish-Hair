package com.inq.wishhair.wesharewishhair.fixture;

import com.inq.wishhair.wesharewishhair.domain.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.domain.hashtag.HashTag;
import com.inq.wishhair.wesharewishhair.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.domain.photo.entity.Photo;
import com.inq.wishhair.wesharewishhair.domain.user.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public enum HairStyleFixture {

    A("hairStyleA", new ArrayList<>(List.of("test1.png", "test2.png", "test3.png", "test4.png")),
            new ArrayList<>(List.of(Tag.PERM, Tag.LONG, Tag.SQUARE, Tag.UPSTAGE)), Sex.WOMAN),
    B("hairStyleB", new ArrayList<>(List.of("test5.png", "test6.png", "test7.png", "test8.png")),
            new ArrayList<>(List.of(Tag.NO_PERM, Tag.SHORT, Tag.NEAT, Tag.OVAL)), Sex.MAN),
    C("hairStyleC", new ArrayList<>(List.of("test9.png", "test10.png", "test11.png", "test12.png")),
            new ArrayList<>(List.of(Tag.PERM, Tag.SHORT, Tag.MEDIUM, Tag.FORMAL, Tag.OBLONG)), Sex.MAN);

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

    private String createStoreFilename(String originalFilename) {
        String ext = getExt(originalFilename);
        return UUID.randomUUID() + ext;
    }

    private String getExt(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        return originalFilename.substring(index);
    }
}
