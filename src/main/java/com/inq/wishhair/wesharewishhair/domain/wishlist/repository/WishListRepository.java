package com.inq.wishhair.wesharewishhair.domain.wishlist.repository;

import com.inq.wishhair.wesharewishhair.domain.wishlist.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    @Query("select w from WishList w " +
            "where w.user.id = :userId " +
            "order by w.createdDate desc")
    public List<WishList> findByUser(@Param("userId") Long userId);
}
