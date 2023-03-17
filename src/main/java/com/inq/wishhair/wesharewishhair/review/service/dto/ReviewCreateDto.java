package com.inq.wishhair.wesharewishhair.review.service.dto;

import com.inq.wishhair.wesharewishhair.review.enums.Score;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewCreateDto {

    private Long userId;

    private Long hairStyleId;

    private String title;

    private String contents;

    private Score score;

    private List<MultipartFile> files;
}
