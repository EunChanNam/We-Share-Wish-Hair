package com.inq.wishhair.wesharewishhair.photo.service;

import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import com.inq.wishhair.wesharewishhair.photo.domain.PhotoRepository;
import com.inq.wishhair.wesharewishhair.photo.utils.PhotoStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoStore photoStore;
    private final PhotoRepository photoRepository;

    public List<String> uploadPhotos(List<MultipartFile> files) {
        return photoStore.uploadFiles(files);
    }

    @Transactional
    public void deletePhotosByReviewId(Long reviewId, List<Photo> photos) {
        List<String> storeUrls = photos.stream().map(Photo::getStoreUrl).toList();
        photoStore.deleteFiles(storeUrls);
        photoRepository.deleteAllByReview(reviewId);
    }
}
