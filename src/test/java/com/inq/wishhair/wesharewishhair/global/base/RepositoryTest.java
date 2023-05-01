package com.inq.wishhair.wesharewishhair.global.base;

import com.inq.wishhair.wesharewishhair.global.config.JpaAuditingConfig;
import com.inq.wishhair.wesharewishhair.global.config.QueryDslConfig;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.WishHairRepository;
import com.inq.wishhair.wesharewishhair.photo.domain.PhotoRepository;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewRepository;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.LikeReviewRepository;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Import(QueryDslConfig.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JpaAuditingConfig.class
))
public abstract class RepositoryTest {

    @Autowired
    protected PhotoRepository photoRepository;

    @Autowired
    protected WishHairRepository wishHairRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected HairStyleRepository hairStyleRepository;

    @Autowired
    protected ReviewRepository reviewRepository;

    @Autowired
    protected LikeReviewRepository likeReviewRepository;

    @PersistenceContext
    protected EntityManager em;
}
