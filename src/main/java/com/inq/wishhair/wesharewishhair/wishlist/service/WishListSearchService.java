package com.inq.wishhair.wesharewishhair.wishlist.service;

import com.inq.wishhair.wesharewishhair.wishlist.domain.WishList;
import com.inq.wishhair.wesharewishhair.wishlist.domain.WishListSearchRepository;
import com.inq.wishhair.wesharewishhair.wishlist.service.dto.response.WishListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListSearchService {

    private final WishListSearchRepository wishListSearchRepository;

    public Slice<WishListResponse> findWishList(Long userId, Pageable pageable) {
        Slice<WishList> wishLists = wishListSearchRepository.findByUser(userId, pageable);
        return transferContentToResponse(wishLists);
    }

    private Slice<WishListResponse> transferContentToResponse(Slice<WishList> wishLists) {
        return wishLists.map(wishList -> new WishListResponse(wishList.getHairStyle()));
    }
}
