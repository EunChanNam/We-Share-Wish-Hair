package com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.infra.query;

public interface WishHairQueryRepository {

    boolean existByHairStyleIdAndUserId(Long hairStyleId, Long userId);
}
