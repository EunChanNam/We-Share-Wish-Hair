package com.inq.wishhair.wesharewishhair.domain.hashtag;

import com.inq.wishhair.wesharewishhair.domain.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.domain.hashtag.enums.Tag;
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
    private HashTag(Tag tag, HairStyle hairStyle) {
        this.tag = tag;
        this.hairStyle = hairStyle;
    }

    public static HashTag from(HairStyle hairStyle, Tag tag) {
        return new HashTag(tag, hairStyle);
    }
}
