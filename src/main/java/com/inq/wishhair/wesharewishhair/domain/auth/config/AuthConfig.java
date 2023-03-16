package com.inq.wishhair.wesharewishhair.domain.auth.config;

import com.inq.wishhair.wesharewishhair.domain.auth.config.resolver.PayloadResolver;
import com.inq.wishhair.wesharewishhair.domain.auth.config.resolver.TokenResolver;
import com.inq.wishhair.wesharewishhair.domain.auth.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthConfig implements WebMvcConfigurer {

    private final JwtTokenProvider provider;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.addAll(List.of(new TokenResolver(), new PayloadResolver(provider)));
    }
}
