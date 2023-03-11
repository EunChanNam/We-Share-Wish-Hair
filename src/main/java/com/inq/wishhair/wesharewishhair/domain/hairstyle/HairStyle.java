package com.inq.wishhair.wesharewishhair.domain.hairstyle;


import com.inq.wishhair.wesharewishhair.domain.photo.entity.Photo;
import com.inq.wishhair.wesharewishhair.domain.user.enums.Sex;
import jakarta.persistence.*;
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

    @OneToMany(mappedBy = "hairStyle",
            cascade = CascadeType.ALL,
            orphanRemoval = true) // 사진을 값타입 컬렉션 처럼 사용
    private List<Photo> photos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Sex sex;

    //==생성 메서드==//
    private HairStyle(String name, Sex sex, List<Photo> photos) {
        this.name = name;
        this.sex = sex;
        photos.forEach(photo -> photo.registerHairStyle(this));
        this.photos.addAll(photos);
    }

    public static HairStyle createHairStyle(String name, Sex sex, List<Photo> photos) {
        return new HairStyle(name, sex, photos);
    }
}
