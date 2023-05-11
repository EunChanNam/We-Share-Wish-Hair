package com.inq.wishhair.wesharewishhair.user.service.dto.response;

import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class UserResponseAssembler {

    public static PagedResponse<PointResponse> toPagedResponse(Slice<PointHistory> slice) {
        return new PagedResponse<>(transferContentToResponse(slice));
    }

    private static Slice<PointResponse> transferContentToResponse(Slice<PointHistory> slice) {
        return slice.map(PointResponse::new);
    }

    public static MyPageResponse toMyPageResponse(User user, List<ReviewResponse> reviewResponses) {
        return new MyPageResponse(user, reviewResponses);
    }

    public static UserInformation toUserInformation(User user) {
        return new UserInformation(
                user.getEmailValue(),
                user.getName(),
                user.getNicknameValue(),
                user.getSex()
        );
    }
}
