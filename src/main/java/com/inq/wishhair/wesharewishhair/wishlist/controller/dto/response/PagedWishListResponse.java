package com.inq.wishhair.wesharewishhair.wishlist.controller.dto.response;

import com.inq.wishhair.wesharewishhair.global.dto.response.Paging;
import com.inq.wishhair.wesharewishhair.wishlist.service.dto.response.WishListResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@AllArgsConstructor
public class PagedWishListResponse {

    private List<WishListResponse> result;

    private Paging paging;

    public PagedWishListResponse(Slice<WishListResponse> slice) {
        this.result = slice.getContent();
        this.paging = new Paging(slice.getContent().size(), slice.getNumber(), slice.hasNext());
    }
}
