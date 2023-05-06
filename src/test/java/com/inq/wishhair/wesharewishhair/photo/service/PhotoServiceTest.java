package com.inq.wishhair.wesharewishhair.photo.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.utils.MockMultipartFileUtils;
import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.utils.FilenameGenerator.createStoreFilename;
import static com.inq.wishhair.wesharewishhair.global.utils.FilenameGenerator.createUploadLink;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("PhotoService test - SpringBootTest")
public class PhotoServiceTest extends ServiceTest {
    private static final String BUCKET = "bucket";

    @Autowired
    private PhotoService photoService;

    private final List<String> filenames = new ArrayList<>();

    @BeforeEach
    void setUp() {
        for (int i = 1; i <= 3; i++) {
            filenames.add("hello" + i + ".png");
        }
    }

    @Nested
    @DisplayName("이미지 저장 서비스 테스트")
    class uploadPhotos {
        @Test
        @DisplayName("이미지 저장에 성공하고 이미지 URL 을 반환한다")
        void success() throws IOException {
            //given
            List<MultipartFile> files = MockMultipartFileUtils.generateFiles(filenames);
            List<String> expectedUrls = filenames.stream()
                    .map(filename -> createUploadLink(BUCKET, createStoreFilename(filename)))
                    .toList();

            given(photoStore.uploadFiles(files)).willReturn(expectedUrls);

            //when
            List<String> result = photoService.uploadPhotos(files);

            //then
            assertThat(result).containsAll(expectedUrls);
        }

        @Test
        @DisplayName("이미지가 없으면 빈 List 를 반환한다")
        void emptyResult() {
            //given
            List<MultipartFile> files = new ArrayList<>();
            given(photoStore.uploadFiles(files)).willReturn(new ArrayList<>());

            //when
            List<String> result = photoService.uploadPhotos(files);

            //then
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("이미지 파일이 null 이면 실패한다")
        void failByNullFile() {
            //given
            List<MultipartFile> files = new ArrayList<>();
            files.add(null);

            ErrorCode expectedError = ErrorCode.EMPTY_FILE_EX;
            given(photoStore.uploadFiles(files)).willThrow(new WishHairException(expectedError));

            //when, then
            assertThatThrownBy(() -> photoService.uploadPhotos(files))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(expectedError.getMessage());
        }

        @Test
        @DisplayName("이미지 파일이 빈파일이면 실패한다")
        void failByEmptyFile() {
            //given
            List<MultipartFile> files = new ArrayList<>();
            MockMultipartFile emptyFile = new MockMultipartFile("files", "hello1.png", "image/bmp", new byte[]{});
            files.add(emptyFile);

            ErrorCode expectedError = ErrorCode.EMPTY_FILE_EX;
            given(photoStore.uploadFiles(files)).willThrow(new WishHairException(expectedError));

            //when, then
            assertThatThrownBy(() -> photoService.uploadPhotos(files))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(expectedError.getMessage());
        }
    }

    @Test
    @DisplayName("입력받은 리뷰에 대한 모든 이미지를 삭제한다")
    void deletePhotosByReviewId() {
        //given
        Review review = reviewRepository.save(ReviewFixture.A.toEntity(null, null));

        //when
        photoService.deletePhotosByReviewId(review);

        //then
        List<Photo> all = photoRepository.findAll();
        assertThat(all).isEmpty();
    }

    @Test
    @DisplayName("입력받은 리뷰들에 대한 모든 이미지를 삭제한다")
    void deletePhotosByWriter() {
        //given
        List<Review> reviews = new ArrayList<>();
        for (ReviewFixture fixture : ReviewFixture.values()) {
            reviews.add(reviewRepository.save(fixture.toEntity(null, null)));
        }

        //when
        photoService.deletePhotosByWriter(reviews);

        //then
        List<Photo> all = photoRepository.findAll();
        assertThat(all).isEmpty();
    }
}
