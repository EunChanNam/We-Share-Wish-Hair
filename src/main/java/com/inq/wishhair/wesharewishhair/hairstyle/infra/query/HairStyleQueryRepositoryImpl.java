package com.inq.wishhair.wesharewishhair.hairstyle.infra.query;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.QHairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.QHashTag;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.QWishHair;
import com.inq.wishhair.wesharewishhair.user.domain.FaceShape;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HairStyleQueryRepositoryImpl implements HairStyleQueryRepository{

    private final JPAQueryFactory factory;

    private final QHairStyle hairStyle = new QHairStyle("h");
    private final QHashTag hashTag = new QHashTag("t");
    private final QWishHair wish = new QWishHair("w");

    @Override
    public List<HairStyle> findByHashTags(List<Tag> tags, Sex sex, Pageable pageable) {
        return factory
                .select(hairStyle)
                .from(hairStyle)
                .leftJoin(hairStyle.hashTags, hashTag)
                .where(hashTag.tag.in(tags))
                .where(hairStyle.sex.eq(sex))
                .groupBy(hairStyle.id)
                .orderBy(mainQueryOrderBy().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<HairStyle> findByFaceShapeTag(FaceShape faceShape, Sex sex, Pageable pageable) {
        JPAQuery<HairStyle> query = factory
                .select(hairStyle)
                .from(hairStyle)
                .where(hairStyle.sex.eq(sex));

        if (faceShape != null && faceShape.getTag() != null) {
            query.leftJoin(hairStyle.hashTags, hashTag)
                    .where(hashTag.tag.eq(faceShape.getTag()));
        }

        return query
                .orderBy(hairStyle.wishListCount.value.desc(), hairStyle.name.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<HairStyle> findByWish(Long userId, Pageable pageable) {
        return factory
                .select(hairStyle)
                .from(wish)
                .join(wish.hairStyle, hairStyle)
                .where(wish.userId.eq(userId))
                .orderBy(wish.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private List<OrderSpecifier<?>> mainQueryOrderBy() {
        List<OrderSpecifier<?>> orderBy = new LinkedList<>();

        orderBy.add(hairStyle.count().desc());
        orderBy.add(hairStyle.wishListCount.value.desc());
        orderBy.add(hairStyle.name.asc());
        return orderBy;
    }
}
