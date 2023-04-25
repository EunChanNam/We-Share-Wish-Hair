package com.inq.wishhair.wesharewishhair.hairstyle.domain;

import com.inq.wishhair.wesharewishhair.hairstyle.infra.query.HairStyleQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HairStyleRepository extends JpaRepository<HairStyle, Long>, HairStyleQueryRepository {
}
