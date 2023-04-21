package com.inq.wishhair.wesharewishhair.wishlist.domain;

import com.inq.wishhair.wesharewishhair.auditing.BaseEntity;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WishList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hair_style_id")
    private HairStyle hairStyle;

    @JoinColumn
    private Long userId;

    //==생성 메서드==//
    private WishList(HairStyle hairStyle, Long userId) {
        this.hairStyle = hairStyle;
        this.userId = userId;
        this.createdDate = LocalDateTime.now();
    }

    public static WishList createWishList(Long userId, HairStyle hairStyle) {
        return new WishList(hairStyle, userId);
    }

    //==편의 메서드==//
    public boolean isHost(Long userId) {
        return this.userId.equals(userId);
    }
}
