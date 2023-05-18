package com.inq.wishhair.wesharewishhair.review.service.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HashTagResponse;
import com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReviewResponse {

    private Long reviewId;

    private String hairStyleName;

    private String userNickname;

    private String score;

    private String contents;

    private LocalDateTime createdDate;

    private List<PhotoResponse> photos;

    private long likes;

    private List<HashTagResponse> hashTags;

    private boolean isWriter;

    @JsonIgnore
    private Long writerId;

    public void addIsWriter(Long userId) {
        this.isWriter = writerId.equals(userId);
    }
}
