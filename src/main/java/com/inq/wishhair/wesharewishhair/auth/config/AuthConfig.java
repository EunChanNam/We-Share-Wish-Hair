package com.inq.wishhair.wesharewishhair.auth.config;

import com.inq.wishhair.wesharewishhair.auth.utils.JwtTokenProvider;
import com.inq.wishhair.wesharewishhair.auth.config.interceptor.AuthInterceptor;
import com.inq.wishhair.wesharewishhair.auth.config.resolver.PayloadResolver;
import com.inq.wishhair.wesharewishhair.auth.config.resolver.TokenResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthConfig implements WebMvcConfigurer {

    private final JwtTokenProvider provider;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(provider))
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/login", "/api/user", "/api/logout");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.addAll(List.of(new TokenResolver(), new PayloadResolver(provider)));
    }
}
