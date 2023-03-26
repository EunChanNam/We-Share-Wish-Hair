package com.inq.wishhair.wesharewishhair.hairstyle.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class WishListCount {

    @Column(name = "wish_list_count")
    private int value = 0;

    public void plusWishListCount() {
        value++;
    }

    public void minusWishListCount() {
        value--;
    }
}
