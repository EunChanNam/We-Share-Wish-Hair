package com.inq.wishhair.wesharewishhair.wishlist.controller.dto.response;

import com.inq.wishhair.wesharewishhair.wishlist.service.dto.response.WishListResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PagedWishListResponse {

    private List<WishListResponse> result;

    private int contentSize;

    public static PagedWishListResponse of(List<WishListResponse> result) {
        return new PagedWishListResponse(result, result.size());
    }
}
