package com.inq.wishhair.wesharewishhair.wishlist.service;

import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.wishlist.domain.WishList;
import com.inq.wishhair.wesharewishhair.wishlist.domain.WishListSearchRepository;
import com.inq.wishhair.wesharewishhair.wishlist.service.dto.response.WishListResponse;
import com.inq.wishhair.wesharewishhair.wishlist.service.dto.response.WishListResponseAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import static com.inq.wishhair.wesharewishhair.wishlist.service.dto.response.WishListResponseAssembler.toPagedWishListResponse;

@Service
@RequiredArgsConstructor
public class WishListSearchService {

    private final WishListSearchRepository wishListSearchRepository;

    public PagedResponse<WishListResponse> findWishList(Long userId, Pageable pageable) {
        Slice<WishList> wishLists = wishListSearchRepository.findByUser(userId, pageable);
        return toPagedWishListResponse(wishLists);
    }
}
