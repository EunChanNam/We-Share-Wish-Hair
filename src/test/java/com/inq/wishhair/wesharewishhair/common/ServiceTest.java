package com.inq.wishhair.wesharewishhair.common;

import com.inq.wishhair.wesharewishhair.WeShareWishHairApplication;
import com.inq.wishhair.wesharewishhair.point.repository.PointHistoryTestRepository;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {WeShareWishHairApplication.class, PointHistoryTestRepository.class})
public abstract class ServiceTest {
}
