package com.inq.wishhair.wesharewishhair.hairstyle.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface HairStyleRepository extends JpaRepository<HairStyle, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<HairStyle> findWithLockById(Long id);
}
