package com.inq.wishhair.wesharewishhair.hairstyle.service;

import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishlist.WishList;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishlist.WishListSearchRepository;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.WishListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import static com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.WishListResponseAssembler.toPagedWishListResponse;

@Service
@RequiredArgsConstructor
public class WishListSearchService {

    private final WishListSearchRepository wishListSearchRepository;

    public PagedResponse<WishListResponse> findWishList(Long userId, Pageable pageable) {
        Slice<WishList> wishLists = wishListSearchRepository.findByUser(userId, pageable);
        return toPagedWishListResponse(wishLists);
    }
}
