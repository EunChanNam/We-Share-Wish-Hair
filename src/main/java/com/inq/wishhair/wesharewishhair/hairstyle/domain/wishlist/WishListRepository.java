package com.inq.wishhair.wesharewishhair.hairstyle.domain.wishlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select wl from WishList wl " +
            "join fetch wl.hairStyle " +
            "where wl.id = :id")
    Optional<WishList> findWithHairStyleById(@Param("id") Long id);
}
