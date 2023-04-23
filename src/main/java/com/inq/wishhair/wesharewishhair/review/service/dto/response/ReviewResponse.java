package com.inq.wishhair.wesharewishhair.review.service.dto.response;

import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HashTagResponse;
import com.inq.wishhair.wesharewishhair.photo.dto.response.PhotoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
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
}
