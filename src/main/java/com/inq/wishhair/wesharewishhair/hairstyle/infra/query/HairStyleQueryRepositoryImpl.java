package com.inq.wishhair.wesharewishhair.hairstyle.infra.query;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.QHairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.QHashTag;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.QWishHair;
import com.inq.wishhair.wesharewishhair.hairstyle.utils.HairRecommendCondition;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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

    private final NumberExpression<Long> wishCount = new CaseBuilder()
            .when(wish.id.sum().isNull())
            .then(0L)
            .otherwise(hairStyle.id.count());

    @Override
    public List<HairStyle> findByRecommend(HairRecommendCondition condition, Pageable pageable) {
        List<Long> filteredHairStyles = factory
                .select(hairStyle.id)
                .from(hairStyle)
                .innerJoin(hairStyle.hashTags, hashTag).on(hashTagEqFaceShape(condition.getUserFaceShape()))
                .groupBy(hairStyle.id)
                .fetch();

        return factory
                .select(hairStyle)
                .from(hairStyle)
                .innerJoin(hairStyle.hashTags, hashTag)
                .where(
                        hashTagInTags(condition.getTags()),
                        hairStyleIn(filteredHairStyles),
                        hairStyle.sex.eq(condition.getSex()))
                .groupBy(hairStyle.id)
                .orderBy(mainOrderBy())
                .fetch();
    }

    @Override
    public List<HairStyle> findByFaceShape(HairRecommendCondition condition, Pageable pageable) {
        List<Long> filteredHairStyles = factory
                .select(hairStyle.id)
                .from(hairStyle)
                .innerJoin(hairStyle.hashTags, hashTag)
                .where(
                        hashTagEqFaceShape(condition.getUserFaceShape()),
                        hairStyle.sex.eq(condition.getSex()))
                .groupBy(hairStyle.id)
                .fetch();

        return factory
                .select(hairStyle)
                .from(hairStyle)
                .leftJoin(wish).on(hairStyle.id.eq(wish.hairStyleId))
                .where(hairStyleIn(filteredHairStyles))
                .groupBy(hairStyle.id)
                .orderBy(subOrderBy())
                .fetch();
    }

    @Override
    public Slice<HairStyle> findByWish(Long userId, Pageable pageable) {
        List<HairStyle> hairStyles = factory
                .select(hairStyle)
                .from(wish)
                .innerJoin(hairStyle).on(wish.hairStyleId.eq(hairStyle.id))
                .where(wish.userId.eq(userId))
                .orderBy(wish.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L)
                .fetch();

        return new SliceImpl<>(hairStyles, pageable, validateHasNext(pageable, hairStyles));
    }

    private BooleanExpression hairStyleIn(List<Long> filteredHairStyles) {
        return (filteredHairStyles != null) ? hairStyle.id.in(filteredHairStyles) : null;
    }

    private BooleanExpression hashTagInTags(List<Tag> tags) {
        return hashTag.tag.in(tags);
    }

    private BooleanExpression hashTagEqFaceShape(Tag faceShpaeTag) {
        return (faceShpaeTag != null) ? hashTag.tag.eq(faceShpaeTag) : null;
    }

    private boolean validateHasNext(Pageable pageable, List<HairStyle> result) {
        if (result.size() > pageable.getPageSize()) {
            result.remove(pageable.getPageSize());
            return true;
        }
        return false;
    }

    private OrderSpecifier<?>[] mainOrderBy() {
        List<OrderSpecifier<?>> orderBy = new LinkedList<>();

        orderBy.add(hairStyle.id.count().desc());
        orderBy.add(hairStyle.name.asc());

        return orderBy.toArray(OrderSpecifier[]::new);
    }

    private OrderSpecifier<?>[] subOrderBy() {
        List<OrderSpecifier<?>> orderBy = new LinkedList<>();

        orderBy.add(wishCount.desc());
        orderBy.add(hairStyle.name.asc());

        return orderBy.toArray(OrderSpecifier[]::new);
    }
}
