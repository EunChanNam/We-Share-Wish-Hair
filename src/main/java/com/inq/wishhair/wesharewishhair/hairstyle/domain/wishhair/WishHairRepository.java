package com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface WishHairRepository extends JpaRepository<WishHair, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select wl from WishHair wl " +
            "join fetch wl.hairStyle " +
            "where wl.id = :id")
    Optional<WishHair> findWithHairStyleById(@Param("id") Long id);
}
