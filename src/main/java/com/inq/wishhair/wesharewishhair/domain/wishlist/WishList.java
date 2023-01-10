package com.inq.wishhair.wesharewishhair.domain.wishlist;

import com.inq.wishhair.wesharewishhair.domain.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hair_style_id")
    private HairStyle hairStyle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //==생성 메서드==//
    @Builder
    public WishList() {
    }

    //==편의 메서드==//
    public void registerHairStyle(HairStyle hairStyle) {
        this.hairStyle = hairStyle;
    }

    public void registerUser(User user) {
        this.user = user;
    }
}
