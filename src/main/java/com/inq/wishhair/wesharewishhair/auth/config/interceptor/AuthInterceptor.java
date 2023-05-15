package com.inq.wishhair.wesharewishhair.auth.config.interceptor;

import com.inq.wishhair.wesharewishhair.auth.utils.AuthorizationExtractor;
import com.inq.wishhair.wesharewishhair.auth.utils.JwtTokenProvider;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider provider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        validateToken(request);
        return true;
    }

    private void validateToken(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);

        if (!provider.isValidToken(token)) {
            throw new WishHairException(ErrorCode.AUTH_INVALID_TOKEN);
        }
    }
}
