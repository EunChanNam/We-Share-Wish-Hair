package com.inq.wishhair.wesharewishhair.wishlist.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    @Override
    @EntityGraph(attributePaths = "hairStyle")
    Optional<WishList> findById(Long wishListId);
}
