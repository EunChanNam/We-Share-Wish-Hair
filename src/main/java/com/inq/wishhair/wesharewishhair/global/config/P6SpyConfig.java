package com.inq.wishhair.wesharewishhair.global.config;

import com.p6spy.engine.spy.P6SpyOptions;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;


@Configuration
public class P6SpyConfig {
    @PostConstruct
    public void setLogMessageFormatter() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(P6SpyFormatter.class.getName());
    }
}
