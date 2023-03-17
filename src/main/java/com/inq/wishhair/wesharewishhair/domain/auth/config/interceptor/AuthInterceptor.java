package com.inq.wishhair.wesharewishhair.domain.auth.config.interceptor;

import com.inq.wishhair.wesharewishhair.domain.auth.utils.AuthorizationExtractor;
import com.inq.wishhair.wesharewishhair.domain.auth.utils.JwtTokenProvider;
import com.inq.wishhair.wesharewishhair.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.exception.WishHairException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider provider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

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
