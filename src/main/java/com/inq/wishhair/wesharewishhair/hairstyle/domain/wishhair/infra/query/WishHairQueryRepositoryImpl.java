package com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.infra.query;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.QWishHair;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WishHairQueryRepositoryImpl implements WishHairQueryRepository{

    private final JPAQueryFactory factory;

    private final QWishHair wishHair = new QWishHair("w");

    @Override
    public boolean existByHairStyleIdAndUserId(Long hairStyleId, Long userId) {
        return factory
                .select(wishHair)
                .from(wishHair)
                .where(wishHair.userId.eq(userId))
                .where(wishHair.hairStyle.id.eq(hairStyleId))
                .fetchFirst() != null;
    }
}
