package com.inq.wishhair.wesharewishhair.wishlist.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    @Query("select distinct w from WishList w " +
            "join fetch w.hairStyle h " +
            "left outer join fetch h.photos " +
            "where w.user.id = :userId " +
            "order by w.createdDate desc")
    List<WishList> findByUser(@Param("userId") Long userId, Pageable pageable);
}
