package com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair;

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
public class WishHair extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    private Long hairStyleId;

    @JoinColumn
    private Long userId;

    //==생성 메서드==//
    private WishHair(Long hairStyleId, Long userId) {
        this.hairStyleId = hairStyleId;
        this.userId = userId;
        this.createdDate = LocalDateTime.now();
    }

    public static WishHair createWishList(Long userId, Long hairStyleId) {
        return new WishHair(hairStyleId, userId);
    }
}
