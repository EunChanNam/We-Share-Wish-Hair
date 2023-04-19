package com.inq.wishhair.wesharewishhair.global.base;

import com.inq.wishhair.wesharewishhair.auth.domain.TokenRepository;
import com.inq.wishhair.wesharewishhair.auth.utils.JwtTokenProvider;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleSearchRepository;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewRepository;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.LikeReviewRepository;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointRepository;
import com.inq.wishhair.wesharewishhair.wishlist.domain.WishListRepository;
import com.inq.wishhair.wesharewishhair.wishlist.domain.WishListSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
public abstract class ServiceTest {

    @Autowired
    protected WishListSearchRepository wishListSearchRepository;

    @Autowired
    protected WishListRepository wishListRepository;

    @Autowired
    protected PointRepository pointRepository;

    @Autowired
    protected HairStyleRepository hairStyleRepository;

    @Autowired
    protected HairStyleSearchRepository hairStyleSearchRepository;

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
