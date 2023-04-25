package com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WishHairRepository extends JpaRepository<WishHair, Long> {

    void deleteByHairStyleIdAndUserId(Long hairStyleId, Long userId);

    boolean existsByHairStyleIdAndUserId(Long hairStyleId, Long userId);
}
