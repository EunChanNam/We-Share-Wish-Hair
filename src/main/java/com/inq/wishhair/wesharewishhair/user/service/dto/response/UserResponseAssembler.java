package com.inq.wishhair.wesharewishhair.user.service.dto.response;

import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class UserResponseAssembler {

    public static PagedResponse<PointResponse> toPagedResponse(Slice<PointHistory> slice) {
        return new PagedResponse<>(transferContentToResponse(slice));
    }

    private static Slice<PointResponse> transferContentToResponse(Slice<PointHistory> slice) {
        return slice.map(PointResponse::new);
    }
}
