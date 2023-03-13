package com.inq.wishhair.wesharewishhair.domain.hairstyle.repository;

import com.inq.wishhair.wesharewishhair.domain.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.domain.user.enums.Sex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface HairStyleRepository extends JpaRepository<HairStyle, Long> {

    @Query("select h from HairStyle h " +
            "join HashTag t on h.id = t.hairStyle.id " +
            "where t.tag in :tags " +
            "and h.sex = :sex " +
            "group by h.id " +
            "order by count(h.id) desc, h.name")
    List<HairStyle> findByHashTags(@Param("tags") List<Tag> tags,
                                   @Param("sex") Sex sex,
                                   Pageable pageable);
}
