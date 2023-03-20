package com.inq.wishhair.wesharewishhair.hairstyle.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("HairStyleServiceTest - SpringBootTest")
public class HairStyleServiceTest extends ServiceTest {

    @Autowired
    private HairStyleService hairStyleService;

    @Nested
    @DisplayName("헤어스타일 추천 로직")
    class findRecommendedHairStyle {

    }
}
