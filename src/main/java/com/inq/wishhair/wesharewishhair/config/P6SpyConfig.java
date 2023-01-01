package com.inq.wishhair.wesharewishhair.config;

import com.p6spy.engine.spy.P6SpyOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;


@Configuration
public class P6SpyConfig {
    @PostConstruct
    public void setLogMessageFormatter() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(P6SpyFormatter.class.getName());
    }
}
