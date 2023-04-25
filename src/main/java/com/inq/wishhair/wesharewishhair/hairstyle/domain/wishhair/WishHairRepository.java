package com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.infra.query.WishHairQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface WishHairRepository extends JpaRepository<WishHair, Long>, WishHairQueryRepository {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select wl from WishHair wl " +
            "join fetch wl.hairStyle " +
            "where wl.id = :id")
    Optional<WishHair> findWithHairStyleById(@Param("id") Long id);

    @Query("delete from WishHair w " +
            "where w.hairStyle.id = :hairStyleId " +
            "and w.userId = :userId")
    void deleteByHairStyleAndUser(@Param("hairStyleId") Long hairStyleId,
                                  @Param("userId") Long userId);
}
