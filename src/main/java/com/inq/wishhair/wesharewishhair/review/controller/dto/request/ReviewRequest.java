package com.inq.wishhair.wesharewishhair.review.controller.dto.request;

import com.inq.wishhair.wesharewishhair.review.enums.Score;
import com.inq.wishhair.wesharewishhair.review.service.dto.ReviewCreateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewRequest {

    private String contents;

    private Score score;

    private List<MultipartFile> files = new ArrayList<>();

    private Long hairStyleId;

    public ReviewCreateDto toReviewCreateDto(Long userId) {
        return new ReviewCreateDto(
                userId,
                this.hairStyleId,
                this.contents,
                this.score,
                this.files
        );
    }
}
