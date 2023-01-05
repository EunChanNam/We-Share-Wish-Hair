package com.inq.wishhair.wesharewishhair.web.hairstyle.dto.response;

import com.inq.wishhair.wesharewishhair.domain.hairstyle.photo.Photo;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HairStyleResponse {

    private String name;

    private final List<Photo> photos = new ArrayList<>();
}
