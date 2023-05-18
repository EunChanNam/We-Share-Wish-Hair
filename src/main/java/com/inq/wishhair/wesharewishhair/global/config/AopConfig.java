package com.inq.wishhair.wesharewishhair.global.config;

import com.inq.wishhair.wesharewishhair.global.aop.aspect.AddIsWriterAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AopConfig {

    @Bean
    public AddIsWriterAspect addIsWriterAspect() {
        return new AddIsWriterAspect();
    }
}
