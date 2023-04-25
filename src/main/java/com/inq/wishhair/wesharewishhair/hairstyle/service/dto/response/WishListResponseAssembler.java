package com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response;

import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishlist.WishList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class WishListResponseAssembler {

    public static PagedResponse<WishListResponse> toPagedWishListResponse(Slice<WishList> wishLists) {
        return new PagedResponse<>(transferContentToResponse(wishLists));
    }

    private static Slice<WishListResponse> transferContentToResponse(Slice<WishList> wishLists) {
        return wishLists.map(wishList -> new WishListResponse(wishList.getHairStyle()));
    }
}
