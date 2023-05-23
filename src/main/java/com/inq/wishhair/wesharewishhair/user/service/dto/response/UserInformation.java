package com.inq.wishhair.wesharewishhair.user.service.dto.response;

import com.inq.wishhair.wesharewishhair.user.domain.Sex;

public record UserInformation(
        String email,
        String name,
        String nickname,
        Sex sex
) {
}
