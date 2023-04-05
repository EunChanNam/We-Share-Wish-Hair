package com.inq.wishhair.wesharewishhair.hairstyle.domain;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HairStyleSearchRepository extends JpaRepository<HairStyle, Long> {

    @Query("select h from HairStyle h " +
            "join h.hashTags t " +
            "where t.tag in :tags " +
            "and h.sex = :sex " +
            "group by h.id " +
            "order by count(h.id) desc, h.wishListCount.value desc, h.name")
    List<HairStyle> findByHashTags(@Param("tags") List<Tag> tags,
                                   @Param("sex") Sex sex,
                                   Pageable pageable);

    @Query("select h from HairStyle h " +
            "join h.hashTags t " +
            "where t.tag = :tag " +
            "and h.sex = :sex " +
            "order by h.wishListCount.value desc, h.name")
    List<HairStyle> findByFaceShapeTag(@Param("tag") Tag tag,
                                       @Param("sex") Sex sex,
                                       Pageable pageable);

    @Query("select h from HairStyle h " +
            "where h.sex = :sex " +
            "order by h.wishListCount.value desc, h.name")
    List<HairStyle> findByNoFaceShapeTag(@Param("sex") Sex sex,
                                         Pageable pageable);
}
