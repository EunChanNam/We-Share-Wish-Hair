package com.inq.wishhair.wesharewishhair.global.config;

import com.inq.wishhair.wesharewishhair.global.aop.aspect.AddIsWriterAspect;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@TestConfiguration
@EnableAspectJAutoProxy
public class AopConfig {

    @Bean
    public AddIsWriterAspect addIsWriterAspect() {
        return new AddIsWriterAspect();
    }
}
