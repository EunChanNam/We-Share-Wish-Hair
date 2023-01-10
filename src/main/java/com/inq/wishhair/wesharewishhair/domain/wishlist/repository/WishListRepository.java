package com.inq.wishhair.wesharewishhair.domain.wishlist.repository;

import com.inq.wishhair.wesharewishhair.domain.wishlist.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {
}
