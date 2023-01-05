package com.inq.wishhair.wesharewishhair.domain.hairstyle.repository;

import com.inq.wishhair.wesharewishhair.domain.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.domain.hashtag.enums.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HairStyleRepository extends JpaRepository<HairStyle, Long> {

    @Query("select h from HairStyle h " +
            "join HashTag t on h.id = t.hairStyle.id " +
            "where t.tag in :tags " +
            "group by h.id " +
            "having count(h.id) >= :size")
    List<HairStyle> findByHashTags(@Param("tags") List<Tag> tags, @Param("size") int size);
}
