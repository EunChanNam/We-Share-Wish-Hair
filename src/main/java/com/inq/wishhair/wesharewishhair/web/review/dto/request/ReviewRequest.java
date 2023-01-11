package com.inq.wishhair.wesharewishhair.web.review.dto.request;

import com.inq.wishhair.wesharewishhair.domain.review.enums.Score;
import com.inq.wishhair.wesharewishhair.domain.review.service.dto.ReviewCreateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewRequest {

    private String title;

    private String contents;

    private Score score;

    private List<MultipartFile> files = new ArrayList<>();

    public ReviewCreateDto toReviewCreateDto(Long userId) {
        return new ReviewCreateDto(
                userId,
                this.title,
                this.contents,
                this.score,
                this.files
        );
    }
}
