package com.inq.wishhair.wesharewishhair.global.base;

import com.inq.wishhair.wesharewishhair.auth.domain.TokenRepository;
import com.inq.wishhair.wesharewishhair.auth.utils.JwtTokenProvider;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.photo.domain.PhotoRepository;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewRepository;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.LikeReviewRepository;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointRepository;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.WishHairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
public abstract class ServiceTest {

    @Autowired
    protected WishHairRepository wishHairRepository;

    @Autowired
    protected PhotoRepository photoRepository;

    @Autowired
    protected PointRepository pointRepository;

    @Autowired
    protected HairStyleRepository hairStyleRepository;

    @Autowired
    protected TokenRepository tokenRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ReviewRepository reviewRepository;

    @Autowired
    protected LikeReviewRepository likeReviewRepository;

    @Autowired
    protected JwtTokenProvider provider;

    @PersistenceContext
    protected EntityManager em;
}
