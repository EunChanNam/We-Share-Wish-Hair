package com.inq.wishhair.wesharewishhair.domain.review.service.dto.request;

import com.inq.wishhair.wesharewishhair.domain.review.enums.Score;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewRequestDto {

    private Long userId;

    private String title;

    private String contents;

    private Score score;

    private List<MultipartFile> files;
}
