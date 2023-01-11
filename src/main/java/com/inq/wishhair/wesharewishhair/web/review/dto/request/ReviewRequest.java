package com.inq.wishhair.wesharewishhair.web.review.dto.request;

import com.inq.wishhair.wesharewishhair.domain.review.enums.Score;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ReviewRequest {

    private Long userId;

    private String title;

    private String contents;

    private Score score;

    private List<MultipartFile> files = new ArrayList<>();
}
