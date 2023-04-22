package com.inq.wishhair.wesharewishhair.hairstyle.domain;

import com.inq.wishhair.wesharewishhair.hairstyle.infra.query.HairStyleQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface HairStyleRepository extends JpaRepository<HairStyle, Long>, HairStyleQueryRepository {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<HairStyle> findWithLockById(Long id);
}
