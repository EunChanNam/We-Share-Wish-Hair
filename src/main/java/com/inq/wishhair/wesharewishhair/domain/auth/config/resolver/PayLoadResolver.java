package com.inq.wishhair.wesharewishhair.domain.auth.config.resolver;

import com.inq.wishhair.wesharewishhair.domain.auth.utils.AuthorizationExtractor;
import com.inq.wishhair.wesharewishhair.domain.auth.utils.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class PayLoadResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider provider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        String token = AuthorizationExtractor.extract(request);
        return provider.getId(token);
    }
}
