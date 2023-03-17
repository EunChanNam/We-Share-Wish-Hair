package com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HashTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hair_style_id")
    private HairStyle hairStyle;

    //==생성 메서드==//
    private HashTag(Tag tag) {
        this.tag = tag;
    }

    public static HashTag of(Tag tag) {
        return new HashTag(tag);
    }

    //==편의 메서드==//
    public void registerHairStyle(HairStyle hairStyle) {
        this.hairStyle = hairStyle;
    }
}
