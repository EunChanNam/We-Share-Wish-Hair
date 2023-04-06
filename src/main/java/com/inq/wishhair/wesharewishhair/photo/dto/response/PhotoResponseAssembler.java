package com.inq.wishhair.wesharewishhair.photo.dto.response;

import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class PhotoResponseAssembler {

    public static List<PhotoResponse> toPhotoResponses(List<Photo> photos) {
        return photos.stream().map(PhotoResponse::new).toList();
    }
}
