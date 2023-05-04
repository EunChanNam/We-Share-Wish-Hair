package com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishHairRepository extends JpaRepository<WishHair, Long> {

    @Modifying
    @Query("delete from WishHair w where w.hairStyleId = :hairStyleId and w.userId = :userId")
    void deleteByHairStyleIdAndUserId(@Param("hairStyleId") Long hairStyleId,
                                      @Param("userId") Long userId);

    boolean existsByHairStyleIdAndUserId(Long hairStyleId, Long userId);
}
