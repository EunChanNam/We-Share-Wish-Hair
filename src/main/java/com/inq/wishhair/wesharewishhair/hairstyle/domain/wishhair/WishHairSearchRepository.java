package com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishHairSearchRepository extends JpaRepository<WishHair, Long> {

    @Query("select w from WishHair w " +
            "join fetch w.hairStyle h " +
            "where w.userId = :userId " +
            "order by w.id desc")
    Slice<WishHair> findByUser(@Param("userId") Long userId, Pageable pageable);
}
