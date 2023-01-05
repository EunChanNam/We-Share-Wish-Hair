package com.inq.wishhair.wesharewishhair.domain.hairstyle;

import com.inq.wishhair.wesharewishhair.domain.hairstyle.repository.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.domain.hashtag.enums.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class HairStyleRepositoryTest {

    @Autowired
    private HairStyleRepository hairStyleRepository;

    @Test
    void queryTest() {
        List<Tag> tags = new ArrayList<>();
        tags.add(Tag.긴머리);
        tags.add(Tag.펌O);

        List<HairStyle> hairStyles = hairStyleRepository.findByHashTags(tags, tags.size());
        assertThat(hairStyles.get(0).getName()).isEqualTo("헤일로 펌");
        assertThat(hairStyles.size()).isEqualTo(1);
        assertThat(hairStyles.get(0).getPhotos().size()).isEqualTo(4);
    }
}