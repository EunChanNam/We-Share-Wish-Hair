package com.inq.wishhair.wesharewishhair.hairstyle.domain.wishlist;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishListSearchRepository extends JpaRepository<WishList, Long> {

    @Query("select w from WishList w " +
            "join fetch w.hairStyle h " +
            "where w.userId = :userId " +
            "order by w.id desc")
    Slice<WishList> findByUser(@Param("userId") Long userId, Pageable pageable);
}
