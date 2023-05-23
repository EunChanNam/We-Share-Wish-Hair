package com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;

import javax.persistence.*;
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
    private HashTag(HairStyle hairStyle, Tag tag) {
        this.hairStyle = hairStyle;
        this.tag = tag;
    }

    public static HashTag of(HairStyle hairStyle, Tag tag) {
        return new HashTag(hairStyle, tag);
    }

    //==편의 메서드==//
    public String getDescription() {
        return tag.getDescription();
    }
}
